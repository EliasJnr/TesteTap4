package com.eliasjr.testetap4.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.eliasjr.testetap4.databinding.ItemListaBinding
import com.eliasjr.testetap4.extensions.toFullUrl
import com.eliasjr.testetap4.interfaces.ListenerItems
import com.eliasjr.testetap4.model.Movie
import kotlinx.android.synthetic.main.item_lista.view.*


class ListMovieAdapter(
    override var items: MutableList<Movie>
) : RecyclerView.Adapter<ListMovieAdapter.ViewHolder>(), ListenerItems {


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

    inner class ViewHolder(private val binding: ItemListaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.item_lista_movie_cardview.setOnClickListener {
                onItemClick(binding.movie)
            }
        }


        fun bind(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()
            itemView.item_lista_movie_progress_vote_average.text = movie.popularity.toString()
            loadImage(itemView.item_lista_movie_img, movie.poster_path.toFullUrl())

        }
    }

    fun loadImage(view: ImageView, imageUrl: String) {
        Glide.with(view.context)
            .load(imageUrl).apply(RequestOptions().autoClone())
            .into(view)
    }
}