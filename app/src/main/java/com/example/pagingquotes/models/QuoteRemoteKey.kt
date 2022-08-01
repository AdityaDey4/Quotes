package com.example.pagingquotes.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remoteKey")
data class QuoteRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)
