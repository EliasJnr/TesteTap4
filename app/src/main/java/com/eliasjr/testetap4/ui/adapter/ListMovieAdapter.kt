package com.eliasjr.testetap4.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.eliasjr.testetap4.R
import com.eliasjr.testetap4.databinding.ItemListaBinding
import com.eliasjr.testetap4.extensions.toUrlImage
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
            itemView.item_lista_movie_progress_vote_average.text = movie.popularity.toString()
            val previewOverview = movie.overview.take(50) + itemView.context.resources.getString(R.string.ret)
            itemView.item_lista_movie_preview_overview.text = previewOverview
            binding.executePendingBindings()
            Glide.with(itemView.item_lista_movie_img.context)
                .load(movie.poster_path.toUrlImage())
                .apply(RequestOptions().autoClone())
                .placeholder(R.drawable.circular_progress_bar)
                .into(itemView.item_lista_movie_img)

        }
    }

}