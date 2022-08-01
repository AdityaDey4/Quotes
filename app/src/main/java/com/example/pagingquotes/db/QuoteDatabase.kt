package com.example.pagingquotes.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pagingquotes.models.QuoteRemoteKey
import com.example.pagingquotes.models.Result

@Database(entities = [Result::class, QuoteRemoteKey::class], version = 1)
abstract class QuoteDatabase: RoomDatabase() {
    abstract fun getQuoteDao(): QuoteDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}