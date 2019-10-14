package com.eliasjr.testetap4.ui.fragments


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

import com.eliasjr.testetap4.R
import com.eliasjr.testetap4.dagger.MainApplication
import com.eliasjr.testetap4.model.Movie
import com.eliasjr.testetap4.repositorios.MovieRepository
import com.eliasjr.testetap4.ui.adapter.ListaMovieAdapter
import com.eliasjr.testetap4.ui.viewmodel.MovieViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_main.view.*
import javax.inject.Inject

class MainFragment : Fragment() {

    lateinit var viewModel: MovieViewModel
    private val disposer = CompositeDisposable()
    lateinit var adapter: ListaMovieAdapter

    var listMovies: MutableList<Movie> = mutableListOf()

    @Inject
    lateinit var movieRepo: MovieRepository

    init {
        MainApplication.fragmentComponent.mainFragInject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        viewModel = ViewModelProviders.of(requireActivity()).get(MovieViewModel::class.java)
        view.recylerListMovie.setHasFixedSize(false)

        adapter = ListaMovieAdapter(listMovies)
        adapter.onItemClick = { movie ->
            movie?.let { movieNotNull ->
                //vaiParaDetalhes(movieNotNull)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

  

}
