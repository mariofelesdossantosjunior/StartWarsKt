package com.mario.startwarskt.api

import android.net.Uri
import com.google.gson.GsonBuilder
import com.mario.startwarskt.Util.Constant.BASE_URL
import com.mario.startwarskt.model.Character
import com.mario.startwarskt.model.Film
import com.mario.startwarskt.model.Movie
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable

/**
 * Created by mario on 9/22/17.
 */
class StartWarsApi {
    var service: StartWarsApiDef

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()

        service = retrofit.create<StartWarsApiDef>(StartWarsApiDef::class.java)

    }

    fun loadMovies(): Observable<Movie> = service.listMovies()
            .flatMap { filmResult -> Observable.from(filmResult.results) }
            .map { film -> Movie(film.title, film.episodeId, mutableListOf()) }

    fun loadMoviesFull(): Observable<Movie> {
        return service.listMovies()
                .flatMap { filmResults -> Observable.from(filmResults.results) }
                .flatMap { film ->
                    Observable.zip(
                            Observable.just(Movie(film.title, film.episodeId, ArrayList<Character>())),
                            Observable.from(film.personUrls)
                                    .flatMap { personUrl ->
                                        service.loadPerson(Uri.parse(personUrl).lastPathSegment)
                                    }
                                    .map { person ->
                                        Character(person!!.name, person.gender)
                                    }
                                    .toList(),
                            { movie, characters ->
                                movie.characters.addAll(characters)
                                movie
                            })
                }
    }
}