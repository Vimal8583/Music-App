package com.example.androidpractice.musicApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object{
        private var retrofit: Retrofit? = null

        fun init(): ApiServices {

            if (retrofit == null) {

                retrofit = Retrofit.Builder()
                    .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(ApiServices::class.java)
        }


    }
}