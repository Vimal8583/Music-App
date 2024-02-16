package com.example.androidpractice.musicApi

import com.google.gson.annotations.SerializedName
import java.time.Duration

data class Song(
    var id : Long,
    val title : String,
    val duration: Int,
    var artist: Artist,
    @SerializedName("preview")
   val music : String
)
