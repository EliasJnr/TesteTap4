package com.eliasjr.testetap4.ui.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.eliasjr.testetap4.R
import com.eliasjr.testetap4.dagger.MainApplication
import com.eliasjr.testetap4.ui.viewmodel.MovieViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_movie_details.view.*

class MovieDetailsFragment : Fragment() {

    private lateinit var viewModel: MovieViewModel
    private val disposer = CompositeDisposable()

    init {
        MainApplication.fragmentComponent.detailsFragInject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)
        viewModel = ViewModelProviders.of(requireActivity()).get(MovieViewModel::class.java)


        disposer.add(viewModel.movieDetails
            .subscribe {
                Log.d("MovieDetailsLog", it.toString())
                view.titleMovie.text = it?.title
            })


        return view
    }


}
