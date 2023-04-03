package com.maverick.paging3democc.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.maverick.paging3democc.QuoteDatabase
import com.maverick.paging3democc.network.QuoteAPI
import com.maverick.paging3democc.paging.QuotePagingSource
import com.maverick.paging3democc.paging.QuoteRemoteMediator
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class QuoteRepository @Inject constructor(
    private val quoteAPI: QuoteAPI,
    private val quoteDatabase: QuoteDatabase
) {

    fun getQuotes() = Pager(
        config = PagingConfig(
            pageSize = 20, maxSize = 100
        ),
        remoteMediator = QuoteRemoteMediator(quoteAPI, quoteDatabase),
        pagingSourceFactory = { QuotePagingSource(quoteAPI) }
    ).liveData

}