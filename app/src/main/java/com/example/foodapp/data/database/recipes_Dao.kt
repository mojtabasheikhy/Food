package com.example.foodapp.data.database

import androidx.room.*
import com.example.foodapp.data.database.entity.FavoriteEntity
import com.example.foodapp.data.database.entity.joke_entity
import com.example.foodapp.data.database.entity.recipes_entity
import com.example.foodapp.model.Joke
import com.example.foodapp.utils.Const_var
import kotlinx.coroutines.flow.Flow


@Dao
interface recipes_Dao {
    //dao for local catch
    //میخوایم دستورات جدید با دستورات قدیم ریپلیس بشه چون ممکنه دستورات غذایی تغییر کند
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert_newdata(recipesEntity: recipes_entity)

    @Query("SELECT * FROM  ${Const_var.recipes_database_table_name} ORDER BY id ASC")
    fun read_recipes():Flow<List<recipes_entity>>


    ///dao for favorite table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert_favorite(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM ${Const_var.favorite_database_table_name} ORDER BY id ASC")
     fun readfavorite():Flow<List<FavoriteEntity>>

    @Delete
    suspend fun delete_favorite(favoriteEntity: FavoriteEntity)

    @Query("DELETE  FROM ${Const_var.favorite_database_table_name}")
    suspend fun deletealldata()

    //foodjoke
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert_joke(jokeEntity: joke_entity)

    @Query("SELECT * FROM  ${Const_var.joke_database_table_name} ORDER BY id ASC")
    fun read_joke():Flow<List<Joke>>


}