package com.eliasjr.testetap4.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.eliasjr.testetap4.R
import com.eliasjr.testetap4.dagger.MainApplication
import com.eliasjr.testetap4.model.Movie
import com.eliasjr.testetap4.repositorios.MovieRepository
import com.eliasjr.testetap4.ui.activity.DetailsActivity
import com.eliasjr.testetap4.ui.adapter.ListMovieAdapter
import com.eliasjr.testetap4.ui.viewmodel.MovieViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_main.view.*
import javax.inject.Inject

class MainFragment : Fragment() {

    private lateinit var viewModel: MovieViewModel
    private val disposer = CompositeDisposable()
    private lateinit var adapter: ListMovieAdapter

    private var listMovies: MutableList<Movie> = mutableListOf()

    @Inject
    lateinit var movieRepo: MovieRepository

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
                startDetailsActivity(movieNotNull)
            }
        }

        view.recylerListMovie.adapter = adapter

        adapter.items = listMovies
        adapter.notifyDataSetChanged()

        val subs = viewModel.listMoviesTopRated
            .subscribe {
                listMovies = it.toMutableList()
                adapter.items = listMovies
                adapter.notifyDataSetChanged()
            }

        disposer.add(subs)


        return view
    }


    private fun startDetailsActivity(movie: Movie) {
        val intent = Intent(requireContext(), DetailsActivity::class.java)
        intent.putExtra("movie", movie)
        startActivity(intent)
    }


}
