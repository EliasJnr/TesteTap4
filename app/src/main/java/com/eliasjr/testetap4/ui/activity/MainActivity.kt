package com.eliasjr.testetap4.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.eliasjr.testetap4.R
import com.eliasjr.testetap4.dagger.MainApplication
import com.eliasjr.testetap4.model.Movie
import com.eliasjr.testetap4.repositorios.MovieRepository
import com.eliasjr.testetap4.ui.fragments.MainFragment
import com.eliasjr.testetap4.ui.fragments.SemConexaoFragment
import com.eliasjr.testetap4.ui.viewmodel.MovieViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiConsumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    val disposer = CompositeDisposable()

    private val mainFragment = MainFragment()

    lateinit var viewModel: MovieViewModel

    @Inject
    lateinit var movieRepo: MovieRepository

    init {
        MainApplication.actComponent.mainActInject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        sincronizaDados()

        swipeRefreshAction()

        addFragment(mainFragment, false, "0")
    }


    private fun addFragment(fragment: Fragment, addToBackStack: Boolean, tag: String) {
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()

        if (addToBackStack) {
            ft.addToBackStack(tag)
        }
        ft.replace(R.id.container_frame_back, fragment, tag)
        ft.commitAllowingStateLoss()
    }

    fun sincronizaDados() {
        val subs = sincronizaListaMovie()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(sincronizaConsumer())
        disposer.add(subs)
    }

    fun sincronizaListaMovie(): Single<List<Movie>> {
        return movieRepo.getMoviesTopRated() // Requisição p/ servidor
            .map { resp -> resp.results }
            .flatMap { lista ->
                movieRepo.addOrUpdateMovieInterno(lista)
                    .andThen(movieRepo.getMoviesInterno()) // Lista vinda do banco interno após completar a cadeia
            }
    }

    fun sincronizaConsumer(): BiConsumer<List<Movie>?, Throwable?> {
        return BiConsumer { lista, erro ->
            main_swipe.isRefreshing = false
            lista?.let { viewModel.listMoviesTopRated.onNext(it) }
            erro?.let { semConexao() }
        }
    }

    private fun swipeRefreshAction() {
        main_swipe.setOnRefreshListener {
            sincronizaDados()
        }
    }


    private fun semConexao() {
        addFragment(SemConexaoFragment(), false, "")
    }

    override fun onDestroy() {
        super.onDestroy()
        disposer.dispose()
    }

}

