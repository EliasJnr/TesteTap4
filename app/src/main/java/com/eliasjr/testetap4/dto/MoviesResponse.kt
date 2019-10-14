package com.eliasjr.testetap4.dto

import com.eliasjr.testetap4.model.Movie

data class MoviesResponse(
    var page: Int,
    var totalResults: Int,
    var totalPages: Int,
    var results: List<Movie>
)