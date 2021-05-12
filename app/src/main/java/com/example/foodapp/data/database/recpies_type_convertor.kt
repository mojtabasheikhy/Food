package com.example.foodapp.data.database

import androidx.room.TypeConverter
import com.example.foodapp.model.Result
import com.example.foodapp.model.foodapi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class recpies_type_convertor {

    var gson=Gson()

    @TypeConverter
    fun josnTo_String(foodapi: foodapi):String{
        return gson.toJson(foodapi)
    }

    @TypeConverter
    fun stringTo_json(data:String):foodapi{
        var lisType=object :TypeToken<foodapi>(){}.type
        return gson.fromJson(data,lisType)
    }

    @TypeConverter
    fun resualt_to_stirng(result: Result):String{
        return gson.toJson(result)
    }

    @TypeConverter
    fun string_to_resualt(data: String):Result{
        var lisType=object :TypeToken<Result>(){}.type
        return gson.fromJson(data,lisType)
    }
}