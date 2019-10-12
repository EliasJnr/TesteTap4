package com.eliasjr.testetap4.api

import com.eliasjr.testetap4.dto.MoviesResponse
import com.eliasjr.testetap4.utilities.key
import io.reactivex.Single
import retrofit2.http.GET

interface MovieAPI {

    @GET("movie/top_rated?api_key=$key&language=pt-BR&page=1")
    fun getListMoviesTopRated(): Single<MoviesResponse>
}