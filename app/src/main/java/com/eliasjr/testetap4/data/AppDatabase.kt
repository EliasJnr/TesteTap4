package com.eliasjr.testetap4.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.eliasjr.testetap4.dao.MovieDAO
import com.eliasjr.testetap4.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDAO(): MovieDAO
}