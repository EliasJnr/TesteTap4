package com.eliasjr.testetap4.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.eliasjr.testetap4.R
import com.eliasjr.testetap4.dagger.MainApplication
import com.eliasjr.testetap4.extensions.toUrlImage
import com.eliasjr.testetap4.repositorios.MovieRepository
import com.eliasjr.testetap4.ui.viewmodel.MovieViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_movie_details.view.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovieDetailsFragment : Fragment() {

    private lateinit var viewModel: MovieViewModel
    private val disposer = CompositeDisposable()

    @Inject
    lateinit var movieRepo: MovieRepository

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

                Glide.with(view.img_post.context)
                    .load(it.backdrop_path.toUrlImage())
                    .apply(RequestOptions().autoClone())
                    .into(view.img_post)

                view.tvOverview.text = it.overview
                view.tvTitle.text = it.title

                Observable.intervalRange(
                    1,
                    it.popularity.toLong(),
                    0,
                    100,
                    TimeUnit.MICROSECONDS,
                    AndroidSchedulers.mainThread()
                ).subscribeOn(Schedulers.io()).subscribe {
                    view.progressBar.progress = it.toInt()
                    view.tvPercent.text = "$it%"
                }

                disposer.add(movieRepo.getDetailsMovie(it.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { res ->
                        res?.let {

                        }
                    })
            })
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        disposer.dispose()
    }
}
