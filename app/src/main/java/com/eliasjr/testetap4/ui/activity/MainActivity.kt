package com.eliasjr.testetap4.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.eliasjr.testetap4.R
import com.eliasjr.testetap4.dagger.MainApplication
import com.eliasjr.testetap4.model.Movie
import com.eliasjr.testetap4.repositorios.MovieRepository
import com.eliasjr.testetap4.ui.fragments.MainFragment
import com.eliasjr.testetap4.ui.fragments.MovieDetailsFragment
import com.eliasjr.testetap4.ui.fragments.NoConnectionFragment
import com.eliasjr.testetap4.ui.viewmodel.MovieViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val disposer = CompositeDisposable()

    private val mainFragment = MainFragment()
    private val detailsFragment = MovieDetailsFragment()

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

        disposer.add(viewModel.refresh.subscribe {
            syncsDice()
        })

        disposer.add(viewModel.movieDetails.subscribe {
            addFragment(detailsFragment, true, it.id)
        })

        addFragment(mainFragment, false, null)
    }

    private fun addFragment(fragment: Fragment, addToBackStack: Boolean, id: Int?) {
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        val tag = "android:switcher:${fragment.id}:${id}"
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
            .subscribe { list, error ->
                list?.let {
                    viewModel.listMoviesTopRated.onNext(it)
                }
                error?.let {
                    Log.d("ThrowableMainActivity", it.message.toString())
                    noConnection()
                }
            }
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


    private fun noConnection() {
        addFragment(NoConnectionFragment(), true, null)
    }


    override fun onDestroy() {
        super.onDestroy()
        disposer.dispose()
    }

}

