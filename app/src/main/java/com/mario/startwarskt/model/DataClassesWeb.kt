package com.mario.startwarskt.model

import com.google.gson.annotations.SerializedName

/**
 * Created by mario on 9/22/17.
 */
data class FilmResult(val results: List<Film>)

data class Film(val title: String,
                @SerializedName("episode_id")
                val episodeId: Int,
                @SerializedName("characters")
                val personUrls: List<String>)

data class Person(val name:String,
                  val gender: String)