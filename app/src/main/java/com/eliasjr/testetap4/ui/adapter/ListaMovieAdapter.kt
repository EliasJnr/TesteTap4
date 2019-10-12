package com.eliasjr.testetap4.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.eliasjr.testetap4.databinding.ItemListaBinding
import com.eliasjr.testetap4.extensions.toFullUrl
import com.eliasjr.testetap4.interfaces.listenerItens
import com.eliasjr.testetap4.model.Movie
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_lista.view.*
import java.util.concurrent.TimeUnit


class ListaMovieAdapter(
    override var items: MutableList<Movie>
) : RecyclerView.Adapter<ListaMovieAdapter.ViewHolder>(), listenerItens {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListaBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    var onItemClick: (Movie?) -> Unit = {}
    private val disposer = CompositeDisposable()

    inner class ViewHolder(private val binding: ItemListaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.item_lista_movie_cardview.setOnClickListener {
                onItemClick(binding.movie)
            }
        }

        var subs: Disposable? = null

        fun bind(movie: Movie) {

            binding.movie = movie
            binding.executePendingBindings()

            val voteAverage = movie.popularity.toInt()

            val cont = Observable.intervalRange(
                1,
                voteAverage.toLong(),
                0,
                100,
                TimeUnit.MICROSECONDS,
                AndroidSchedulers.mainThread()
            )
                .subscribeOn(Schedulers.io())


            loadImage(itemView.item_lista_movie_img, movie.poster_path.toFullUrl())

            subs = cont.subscribe {
                itemView.item_lista_movie_progressBar.progress = it.toInt()
                itemView.item_lista_movie_progress_vote_average.text = it.toString()
            }
            disposer.add(subs!!)
        }
    }

    fun loadImage(view: ImageView, imageUrl: String) {
        Glide.with(view.context)
            .load(imageUrl).apply(RequestOptions().autoClone())
            .into(view)
    }
}