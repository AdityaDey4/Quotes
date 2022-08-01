package com.example.pagingquotes.di

import android.content.Context
import androidx.room.Room
import com.example.pagingquotes.db.QuoteDatabase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    fun provideDatabase(@ApplicationContext context: Context): QuoteDatabase {
        return Room.databaseBuilder(context, QuoteDatabase::class.java, "quoteDB").build()
    }
}