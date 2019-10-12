package com.eliasjr.testetap4.dto

import com.eliasjr.testetap4.model.Movie

data class MoviesResponse(
    var page: Int,
    var total_results: Int,
    var total_pages: Int,
    var results: List<Movie>
)