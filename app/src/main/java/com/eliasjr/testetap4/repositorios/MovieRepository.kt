package com.eliasjr.testetap4.repositorios

import com.eliasjr.testetap4.api.MovieAPI
import com.eliasjr.testetap4.dao.MovieDAO
import com.eliasjr.testetap4.model.Movie
import io.reactivex.Completable
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieAPI: MovieAPI,
    private val movieDAO: MovieDAO
) {
    fun getMoviesTopRated() = movieAPI.getListMoviesTopRated()

    fun addOrUpdateMovieIntern(movies: List<Movie>): Completable {
        return Completable.fromAction { movieDAO.insertOrUpdateMovies(movies) }
    }

    fun getMoviesIntern() = movieDAO.getListMovies()

    fun getDetailsMovie(movieId: Int) = movieAPI.getDetailsMovie(movieId)
}