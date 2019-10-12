package com.eliasjr.testetap4.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.eliasjr.testetap4.model.Movie
import io.reactivex.subjects.BehaviorSubject
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.eliasjr.testetap4.utilities.apiImg


class MovieViewModel : ViewModel() {

    val listMoviesTopRated = BehaviorSubject.create<List<Movie>>()


}