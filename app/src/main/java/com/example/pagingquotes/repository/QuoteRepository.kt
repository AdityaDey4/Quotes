package com.example.pagingquotes.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.pagingquotes.api.QuoteApi
import com.example.pagingquotes.paging.QuotePagingSource
import javax.inject.Inject

class QuoteRepository @Inject constructor(private val quoteApi: QuoteApi) {

    fun getQuotes() = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 100),
        pagingSourceFactory = {QuotePagingSource(quoteApi)}
    ).liveData
}