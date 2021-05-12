package com.example.foodapp.model


import com.google.gson.annotations.SerializedName

data class Joke(
    @SerializedName("text")
    val text: String
)