package com.example.qutes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.qutes.data.model.QuoteModel

@Database(entities = [QuoteModel::class], version = 1)
abstract class QuoteDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
}
