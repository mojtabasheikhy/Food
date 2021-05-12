package com.example.foodapp.data

import com.example.foodapp.model.Joke
import com.example.foodapp.model.foodapi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private var foodapi: retrofit_secondurl
) {
    suspend fun get_recipes(query: Map<String,String>):Response<foodapi>{
        return foodapi.get_data_foodapi(query)
    }
    suspend fun search_recipes(search_query: Map<String,String>):Response<foodapi>{
        return foodapi.search_recipes(search_query)
    }
    suspend fun joke(search_query: String):Response<Joke>{
        return foodapi.joke_food(search_query)
    }
}