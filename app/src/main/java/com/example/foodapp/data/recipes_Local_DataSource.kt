package com.example.foodapp.data

import com.example.foodapp.data.database.entity.FavoriteEntity
import com.example.foodapp.data.database.entity.joke_entity
import com.example.foodapp.data.database.entity.recipes_entity
import com.example.foodapp.data.database.recipes_Dao
import com.example.foodapp.model.Joke
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class recipes_Local_DataSource @Inject constructor(
    var recipesDao: recipes_Dao
) {
    suspend fun insert_recipes_local(recipesEntity: recipes_entity){
        recipesDao.insert_newdata(recipesEntity)
    }

    fun read_recipes_local():Flow<List<recipes_entity>>{
        return recipesDao.read_recipes()
    }

    //favorite

    suspend fun insert_favorite_local(favoriteEntity: FavoriteEntity){
        recipesDao.insert_favorite(favoriteEntity)
    }
    fun read_favorite_local():Flow<List<FavoriteEntity>>
    {
        return recipesDao.readfavorite()
    }
    suspend fun deleteonefavorite(favoriteEntity: FavoriteEntity){
        recipesDao.delete_favorite(favoriteEntity)
    }
    suspend fun deleteallfavorite()
    {
        recipesDao.deletealldata()
    }

    //joke
    suspend fun insert_joke_local(jokeEntity: joke_entity){
        recipesDao.insert_joke(jokeEntity)
    }
    fun read_joke_local():Flow<List<Joke>>
    {
        return recipesDao.read_joke()
    }
}
