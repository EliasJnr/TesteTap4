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
import com.eliasjr.testetap4.ui.fragments.NoConnectionFragment
import com.eliasjr.testetap4.ui.viewmodel.MovieViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiConsumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val disposer = CompositeDisposable()

    private val mainFragment = MainFragment()

    private lateinit var viewModel: MovieViewModel

    @Inject
    lateinit var movieRepo: MovieRepository

    init {
        MainApplication.actComponent.mainActInject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        syncsDice()
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

    private fun syncsDice() {
        val subs = syncsListMovie()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(syncConsumer())
        disposer.add(subs)
    }

    private fun syncsListMovie(): Single<List<Movie>> {
        return movieRepo.getMoviesTopRated()
            .map { resp -> resp.results }
            .flatMap { list ->
                movieRepo.addOrUpdateMovieIntern(list)
                    .andThen(movieRepo.getMoviesIntern())
            }
    }

    private fun syncConsumer(): BiConsumer<List<Movie>?, Throwable?> {
        return BiConsumer { list, error ->
            main_swipe.isRefreshing = false
            list?.let { viewModel.listMoviesTopRated.onNext(it) }
            error?.let { noConnection() }
        }
    }

    private fun swipeRefreshAction() {
        main_swipe.setOnRefreshListener {
            syncsDice()
        }
    }


    private fun noConnection() {
        addFragment(NoConnectionFragment(), true, "1")
    }


    override fun onDestroy() {
        super.onDestroy()
        disposer.dispose()
    }

}

