package com.maverick.paging3democc.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class QuoteList(
    val count: Int,
    val lastItemIndex: Int,
    val page: Int,
    val results: List<Result>,
    val totalCount: Int,
    val totalPages: Int,
)

@Entity(tableName = "Quote")
data class Result(
    @PrimaryKey(autoGenerate = false)
    val _id: String,
    val author: String,
    val authorSlug: String,
    val content: String,
    val dateAdded: String,
    val dateModified: String,
    val length: Int,
)

@Entity(tableName = "QuoteRemoteKeys")
data class QuoteRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?,
)