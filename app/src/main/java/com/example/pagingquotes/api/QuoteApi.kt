package com.example.pagingquotes.api

import com.example.pagingquotes.models.QuoteList
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteApi {
    @GET("/quotes")
    suspend fun getQuotes(
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int
    ) : QuoteList
}