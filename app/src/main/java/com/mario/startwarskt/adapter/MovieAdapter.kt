package com.mario.startwarskt.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.mario.startwarskt.R
import com.mario.startwarskt.Util.inflate
import com.mario.startwarskt.model.Movie
import kotlinx.android.synthetic.main.movies_item_row.view.*

/**
 * Created by mario on 9/24/17.
 */
class MovieAdapter(private val listener: (Movie) -> Unit)
    : RecyclerView.Adapter<ViewHolder>() {

    var movies = mutableListOf<Movie>()
        set(elements) {
            field = elements
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.movies_item_row))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(movies[position], listener)

    override fun getItemCount() = movies.size
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(movie: Movie, listener: (Movie) -> Unit) = with(itemView) {
        tv_movies_item_title.text = movie.title
        setOnClickListener { listener(movie) }
    }
}
