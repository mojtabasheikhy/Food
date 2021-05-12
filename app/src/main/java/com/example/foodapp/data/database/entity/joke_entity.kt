package com.example.foodapp.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodapp.model.Joke
import com.example.foodapp.utils.Const_var

@Entity(tableName = Const_var.joke_database_table_name)
class joke_entity(
    @Embedded
    var food:Joke) {
    @PrimaryKey(autoGenerate = false )
    var id:Int = 0
}