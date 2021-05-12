package com.example.foodapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodapp.model.foodapi
import com.example.foodapp.utils.Const_var

@Entity(tableName = Const_var.recipes_database_table_name)
class recipes_entity (var foodrecipes:foodapi ){
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
}
