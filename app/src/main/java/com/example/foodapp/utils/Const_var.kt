package com.example.foodapp.utils

object Const_var {

    //Retrofit
    const val apikey = "f153f999ad214d7e9c9c6de2b5923e17"
    const val baseUrl = "https://api.spoonacular.com/"
    const val baseurl_ingredient="https://spoonacular.com/cdn/ingredients_250x250/"
    const val secondurl = "recipes/complexSearch"
    const val addRecipeInformation = "addRecipeInformation"
    const val fillIngredients = "fillIngredients"
    const val search_query="query"
    const val secondurl_joke="food/jokes/random"


    //Room
    const val recipes_database_name = "recipes_db"
    const val recipes_database_table_name = "recipes_table"
    const val recipes_database_version = 2
    const val favorite_database_table_name="favorite_table"
    const val joke_database_table_name="joke_table"

    //PrefKey
    const val pref_DataStore_Name="DataStore_Food"
    const val pref_key_MealType="MealType"
    const val pref_key_MealType_id="MealType_id"
    const val pref_key_DietType="DietType"
    const val pref_key_DietType_id="DietType_id"
    const val backonline="backonline"


    //bottomSheet
    const val default_mealType = "main course"
    const val default_dietType = "gluten free"
    const val default_number = "50"

    //bundelkey
    const val detail_overview_bundel_key="detail_toOverview"

}