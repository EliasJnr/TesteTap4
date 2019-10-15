package com.eliasjr.testetap4.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.eliasjr.testetap4.R
import com.eliasjr.testetap4.dagger.MainApplication
import com.eliasjr.testetap4.model.Movie
import com.eliasjr.testetap4.ui.adapter.ListMovieAdapter
import com.eliasjr.testetap4.ui.viewmodel.MovieViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MovieViewModel
    private val disposer = CompositeDisposable()
    private lateinit var adapter: ListMovieAdapter

    private var listMovies: MutableList<Movie> = mutableListOf()


    init {
        MainApplication.fragmentComponent.mainFragInject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        viewModel = ViewModelProviders.of(requireActivity()).get(MovieViewModel::class.java)
        view.recylerListMovie.setHasFixedSize(false)

        adapter = ListMovieAdapter(listMovies)
        adapter.onItemClick = { movie ->
            movie?.let { movieNotNull ->
                viewModel.movieDetails.onNext(movieNotNull)
            }
        }

        view.recylerListMovie.adapter = adapter

        adapter.items = listMovies
        adapter.notifyDataSetChanged()

        disposer.add(viewModel.listMoviesTopRated
            .subscribe {
                listMovies = it.toMutableList()
                adapter.items = listMovies
                view.main_swipe.isRefreshing = false
                adapter.notifyDataSetChanged()
            })

        view.main_swipe.setOnRefreshListener {
            viewModel.refresh.onNext(true)
        }

        return view
    }


}
