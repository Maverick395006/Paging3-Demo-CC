package com.maverick.paging3democc.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.maverick.paging3democc.QuoteDatabase
import com.maverick.paging3democc.models.QuoteRemoteKeys
import com.maverick.paging3democc.models.Result
import com.maverick.paging3democc.network.QuoteAPI

@OptIn(ExperimentalPagingApi::class)
class QuoteRemoteMediator(
    private val quoteAPI: QuoteAPI,
    private val quoteDatabase: QuoteDatabase
) : RemoteMediator<Int, Result>() {

    val quoteDao = quoteDatabase.quoteDao()
    val remoteKeysDao = quoteDatabase.remoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Result>): MediatorResult {

        return try {

            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextPage
                }
            }

            val response = quoteAPI.getQuotes(currentPage)
            val endPaginationReached = response.totalPages == currentPage

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endPaginationReached) null else currentPage + 1

            quoteDatabase.withTransaction {
                quoteDao.addQuotes(response.results)
                val keys = response.results.map {
                    QuoteRemoteKeys(
                        id = it._id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                remoteKeysDao.addAllRemoteKeys(keys)
            }

            MediatorResult.Success(endPaginationReached)

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }

        // Fetch Quotes from API
        // Save these Quotes + RemoteKeys Data into DB
        // Logic for States - REFRESH, PREPEND, APPEND

    }

    private suspend fun getRemoteKeyClosestCurrentPosition(state: PagingState<Int, Result>): QuoteRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?._id?.let { id ->
                remoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Result>): QuoteRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { quote ->
            remoteKeysDao.getRemoteKeys(id = quote._id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Result>): QuoteRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { quote ->
            remoteKeysDao.getRemoteKeys(id = quote._id)
        }
    }

}