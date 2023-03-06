package com.example.covidtesttask.di.module

import com.example.covidtesttask.common.Constants
import com.example.covidtesttask.data.remote.CovidApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideCovidApi() = Retrofit.Builder()
        .baseUrl(Constants.COVID_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CovidApi::class.java)
}