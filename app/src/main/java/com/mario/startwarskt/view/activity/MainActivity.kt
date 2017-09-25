package com.mario.startwarskt.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.mario.startwarskt.R
import com.mario.startwarskt.adapter.MovieAdapter
import com.mario.startwarskt.api.StartWarsApi
import com.mario.startwarskt.model.Movie
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.longToast
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    var movies = mutableListOf<Movie>()

    private val adapter by lazy {
        MovieAdapter { movie ->
            longToast("Filme Selecionado ${movie.title}")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecycleView()

        StartWarsApi().loadMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({movie -> showMovieOnIU(movie)},
                        { erro -> erro.printStackTrace()},
                        { adapter.notifyDataSetChanged()})
    }

    private fun setupRecycleView() {
        rv_main_movies.setHasFixedSize(true)
        rv_main_movies.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rv_main_movies.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        rv_main_movies.adapter = adapter
    }

    fun showMovieOnIU(movie: Movie){
        movies.add(movie)
        adapter.movies = movies
    }
}

