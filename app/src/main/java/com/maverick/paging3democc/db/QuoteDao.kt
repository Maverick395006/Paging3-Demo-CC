package com.maverick.paging3democc.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maverick.paging3democc.models.Result

@Dao
interface QuoteDao {

    @Query("SELECT * FROM Quote")
    fun getQuotes(): PagingSource<Int, Result>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuotes(quotes:List<Result>)

    @Query("DELETE FROM Quote")
    suspend fun deleteQuotes()

}