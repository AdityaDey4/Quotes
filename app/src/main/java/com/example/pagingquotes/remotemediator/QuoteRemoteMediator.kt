package com.example.pagingquotes.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.pagingquotes.api.QuoteApi
import com.example.pagingquotes.db.QuoteDatabase
import com.example.pagingquotes.models.QuoteRemoteKey
import com.example.pagingquotes.models.Result
import com.example.pagingquotes.utils.Constant

@ExperimentalPagingApi
class QuoteRemoteMediator(
    private val quoteApi: QuoteApi,
    private val quoteDatabase: QuoteDatabase
): RemoteMediator<Int, Result>() {
    val quoteDao = quoteDatabase.getQuoteDao()
    val remoteKeysDao = quoteDatabase.getRemoteKeysDao()
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Result>): MediatorResult {
        //Fetch quotes from API
        //Save those quotes+remote_keys in database
        //Logic for States - REFRESH, PREPEND, APPEND

        return try {

            val currentPage: Int = when (loadType){
                LoadType.APPEND -> {
                    val remoteKey = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKey?.nextKey?:
                    return MediatorResult.Success(
                        endOfPaginationReached = remoteKey!=null
                    )
                    nextKey
                }
                LoadType.PREPEND -> {
                    val remoteKey = getRemoteKeyForFirstItem(state)
                    val prevkey = remoteKey?.prevKey?:
                    return MediatorResult.Success(
                        endOfPaginationReached = remoteKey!=null
                    )
                    prevkey
                }else -> {
                   // LoadType.REFRESH
                    val remoteKey = getRemoteKeyClosestToCurrPosition(state)
                    remoteKey?.nextKey?.minus(1)?:1
                }
            }


            val response = quoteApi.getQuotes(page = currentPage)
            val isEndPage = currentPage==response.totalPages

            quoteDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    quoteDao.deleteQuotes()
                    remoteKeysDao.deleteAllRemoteKeys()
                }
                quoteDao.addQuotes(response.results)
                val prevKey = if(currentPage==Constant.DEFAULT_PAGE) null else currentPage-1
                val nextKey = if(currentPage==response.totalPages) null else currentPage+1
                val keys = response.results.map { result->
                    QuoteRemoteKey(result._id, prevKey, nextKey)
                }
                remoteKeysDao.addAllRemoteKeys(keys)
            }
            MediatorResult.Success(isEndPage)
        }catch (e: Exception) {
            MediatorResult.Error(e)
        }

    }

    private suspend fun getRemoteKeyClosestToCurrPosition(
        state: PagingState<Int, Result>
    ): QuoteRemoteKey?{
        return state.anchorPosition?.let { position->
            state.closestItemToPosition(position)?._id?.let {
                remoteKeysDao.getRemoteKeys(it)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Result>): QuoteRemoteKey? {
        return state.pages.firstOrNull { firstPage->
            firstPage.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { firstResult->
            remoteKeysDao.getRemoteKeys(firstResult._id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Result>): QuoteRemoteKey? {
        return state.pages.lastOrNull{
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { result->
            remoteKeysDao.getRemoteKeys(result._id)
        }
    }
    /* state.pages.lastOrNull : go to last page or null
    it.data.isNotEmpty(): if the result of the last page is not empty
    data?.lastOrNull(): go to the last record of the last page ot null
    remoteKeysDao.getRemoteKeys(result._id): get it Remotekey(prevkey, nextkey)*/
}