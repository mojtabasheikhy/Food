package com.example.foodapp.di

import android.content.Context
import androidx.room.Room
import com.example.foodapp.data.database.recipes_db
import com.example.foodapp.utils.Const_var
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataBaseModule {


    @Singleton
    @Provides
    fun database_provide(@ApplicationContext Context:Context)=Room.databaseBuilder(Context,recipes_db::class.java,Const_var.recipes_database_name).build()

    @Singleton
    @Provides
    fun database_dao_provide(recipes_db: recipes_db)=recipes_db.recipes_dao()
}