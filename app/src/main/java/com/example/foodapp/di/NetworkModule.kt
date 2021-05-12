package com.example.foodapp.di

import com.example.foodapp.data.retrofit_secondurl
import com.example.foodapp.utils.Const_var
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    @Singleton
    fun providesOkhttp(): OkHttpClient {
        return OkHttpClient.Builder().readTimeout(12, TimeUnit.SECONDS)
            .connectTimeout(12, TimeUnit.SECONDS)
            .build()

    }

    @Provides
    @Singleton
    fun provideGsonconvetfactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun retrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        var retrofit_inst = Retrofit.Builder().addConverterFactory(gsonConverterFactory)
            .baseUrl(Const_var.baseUrl).client(okHttpClient)
            .build()
        return retrofit_inst
    }

    @Singleton
    @Provides
    fun provideApiServices(retrofit: Retrofit): retrofit_secondurl {
        return retrofit.create(retrofit_secondurl::class.java)
    }
}