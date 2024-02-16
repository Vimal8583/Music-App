package com.example.androidpractice.musicApi

import com.google.gson.annotations.SerializedName

data class Artist(
    var id :Long,
    var name : String,
    var type : String,
    @SerializedName("picture")
    var picture : String
)