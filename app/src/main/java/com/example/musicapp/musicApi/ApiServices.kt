package com.example.androidpractice.musicApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiServices {

    @Headers(
        "X-RapidAPI-Key: 1a37a3e7d9msh25fa3bf3bffce26p18af51jsn3c0f0fb5eb66",
        "X-RapidAPI-Host: deezerdevs-deezer.p.rapidapi.com"
    )
    @GET("search")
    fun getSongList(@Query("q") query: String): Call<MusicResponse>
}

