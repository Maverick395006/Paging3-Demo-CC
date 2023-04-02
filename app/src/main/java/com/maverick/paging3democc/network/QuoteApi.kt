package com.maverick.paging3democc.network

import com.maverick.paging3democc.models.QuoteList
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteAPI {

    @GET("quotes")
    suspend fun getQuotes(@Query("page") page: Int): QuoteList

}