package com.example.pagingquotes.db

import androidx.room.*
import com.example.pagingquotes.models.QuoteRemoteKey

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM remoteKey WHERE id =:myId")
    suspend fun getRemoteKeys(myId: String): QuoteRemoteKey

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(keys: List<QuoteRemoteKey>)

    @Query("DELETE FROM remoteKey")
    suspend fun deleteAllRemoteKeys()
}