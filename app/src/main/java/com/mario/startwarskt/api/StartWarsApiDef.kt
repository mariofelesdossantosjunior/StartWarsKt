package com.mario.startwarskt.api

import com.mario.startwarskt.model.FilmResult
import com.mario.startwarskt.model.Person
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Created by mario on 9/22/17.
 */
interface StartWarsApiDef {
    @GET("films")
    fun listMovies(): Observable<FilmResult>

    @GET("people/{personId}")
    fun loadPerson(@Path("personId") personId: String) : Observable<Person>
}