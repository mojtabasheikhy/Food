package com.example.foodapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodapp.model.Result
import com.example.foodapp.utils.Const_var

@Entity(tableName = Const_var.favorite_database_table_name)
class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var result: Result
    )