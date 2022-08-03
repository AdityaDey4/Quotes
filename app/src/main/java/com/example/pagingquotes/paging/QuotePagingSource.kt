package com.example.pagingquotes.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagingquotes.api.QuoteApi
import com.example.pagingquotes.models.Result
import com.example.pagingquotes.utils.Constant
import retrofit2.HttpException
import java.io.IOException

class QuotePagingSource(private val quoteApi: QuoteApi): PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { it->
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1)?:anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val position = params.key ?: Constant.DEFAULT_PAGE
        return try{
            val result = quoteApi.getQuotes(page = position)
            return LoadResult.Page(
                data = result.results,
                prevKey = if(position==Constant.DEFAULT_PAGE) null else position-1,
                nextKey = if(position==result.totalPages) null else position+1
            )
        }catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}