package com.example.foodapp.data

import com.example.foodapp.model.Joke
import com.example.foodapp.model.foodapi
import com.example.foodapp.utils.Const_var
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface retrofit_secondurl {

    @GET(Const_var.secondurl)
    suspend fun get_data_foodapi(@QueryMap query:Map<String,String>):Response<foodapi>

    @GET(Const_var.secondurl)
    suspend fun search_recipes(@QueryMap search_query:Map<String,String>):Response<foodapi>

    @GET(Const_var.secondurl_joke)
    suspend fun joke_food(@Query("apiKey") apikey: String):Response<Joke>
}