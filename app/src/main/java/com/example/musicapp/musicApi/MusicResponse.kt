package com.example.androidpractice.musicApi

import com.google.gson.annotations.SerializedName

data class MusicResponse(
    @SerializedName("data")
   var song : MutableList<Song>,
    var total: Long,
    var next : String
)


