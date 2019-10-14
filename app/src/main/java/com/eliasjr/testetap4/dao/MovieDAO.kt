package com.eliasjr.testetap4.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eliasjr.testetap4.model.Movie
import io.reactivex.Single

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateMovies(movies: List<Movie>)

    @Query("SELECT * FROM movie")
    fun getListMovies(): Single<List<Movie>>
}