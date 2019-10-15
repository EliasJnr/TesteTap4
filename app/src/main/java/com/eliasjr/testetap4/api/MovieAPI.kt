package com.eliasjr.testetap4.api

import com.eliasjr.testetap4.dto.MoviesResponse
import com.eliasjr.testetap4.utilities.key
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieAPI {

    @GET("movie/top_rated?api_key=$key&language=pt-BR&page=1")
    fun getListMoviesTopRated(): Single<MoviesResponse>

    @GET("movie/{movieId}?api_key=$key&language=pt-BR")
    fun getDetailsMovie(@Path("movieId") movieId: Int): Single<Map<Any, Any>>

}