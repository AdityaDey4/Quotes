package com.example.pagingquotes.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.pagingquotes.api.QuoteApi
import com.example.pagingquotes.db.QuoteDatabase
import com.example.pagingquotes.paging.QuotePagingSource
import com.example.pagingquotes.remotemediator.QuoteRemoteMediator
import javax.inject.Inject

class QuoteRepository @Inject constructor(
    private val quoteApi: QuoteApi,
    private val quoteDatabase: QuoteDatabase) {

    @ExperimentalPagingApi
    fun getQuotes() = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 100),
        remoteMediator = QuoteRemoteMediator(quoteApi, quoteDatabase),
        pagingSourceFactory = {quoteDatabase.getQuoteDao().getQuotes()}
    ).liveData
}