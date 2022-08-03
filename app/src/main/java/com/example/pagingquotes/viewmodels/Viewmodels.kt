package com.example.pagingquotes.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.example.pagingquotes.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class Viewmodels @Inject public constructor(repo: QuoteRepository) : ViewModel() {
    @ExperimentalPagingApi
    val list = repo.getQuotes().cachedIn(viewModelScope)
}