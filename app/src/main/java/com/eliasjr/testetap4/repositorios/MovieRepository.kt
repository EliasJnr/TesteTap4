package com.eliasjr.testetap4.repositorios

import com.eliasjr.testetap4.api.MovieAPI
import com.eliasjr.testetap4.dao.MovieDAO
import com.eliasjr.testetap4.model.Movie
import io.reactivex.Completable
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val API: MovieAPI,
    private val movieDAO: MovieDAO
) {
    fun getMoviesTopRated() = API.getListMoviesTopRated()

    fun addOrUpdateMovieInterno(movies: List<Movie>): Completable {
        return Completable.fromAction { movieDAO.insertOrUpdateMovies(movies) }
    }

    fun getMoviesInterno() = movieDAO.getListMovies()
}