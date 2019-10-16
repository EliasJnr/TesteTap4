package com.eliasjr.testetap4.dagger.modules

import android.app.Application
import androidx.room.Room
import com.eliasjr.testetap4.dao.MovieDAO
import com.eliasjr.testetap4.data.AppDatabase
import com.eliasjr.testetap4.utilities.DbName
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule(private val context: Application) {

    @Provides
    @Singleton
    fun provideDatabase(): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DbName)
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideMovieDao(db: AppDatabase): MovieDAO = db.movieDAO()
}