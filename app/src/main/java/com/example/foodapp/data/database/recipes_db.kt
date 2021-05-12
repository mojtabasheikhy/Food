package com.example.foodapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodapp.data.database.entity.FavoriteEntity
import com.example.foodapp.data.database.entity.joke_entity
import com.example.foodapp.data.database.entity.recipes_entity
import com.example.foodapp.utils.Const_var

@Database(
    entities = [recipes_entity::class,FavoriteEntity::class,joke_entity::class ],
    version = Const_var.recipes_database_version,
    exportSchema = false
)
@TypeConverters(recpies_type_convertor::class)
abstract class recipes_db : RoomDatabase() {
    abstract fun recipes_dao(): recipes_Dao
}