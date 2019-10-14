package com.eliasjr.testetap4.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.eliasjr.testetap4.model.Movie
import io.reactivex.subjects.BehaviorSubject


class MovieViewModel : ViewModel() {
    val listMoviesTopRated = BehaviorSubject.create<List<Movie>>()
}