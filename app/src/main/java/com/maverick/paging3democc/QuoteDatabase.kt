package com.maverick.paging3democc

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maverick.paging3democc.db.QuoteDao
import com.maverick.paging3democc.db.RemoteKeysDao
import com.maverick.paging3democc.models.QuoteRemoteKeys
import com.maverick.paging3democc.models.Result

@Database(entities = [Result::class, QuoteRemoteKeys::class], version = 1)
abstract class QuoteDatabase : RoomDatabase() {

    abstract fun quoteDao(): QuoteDao
    abstract fun remoteKeysDao(): RemoteKeysDao

}