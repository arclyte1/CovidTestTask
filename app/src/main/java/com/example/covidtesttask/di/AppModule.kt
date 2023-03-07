package com.example.covidtesttask.di

import android.content.Context
import androidx.room.Room
import com.example.covidtesttask.common.Constants
import com.example.covidtesttask.data.local.CovidDb
import com.example.covidtesttask.data.remote.CovidApi
import com.example.covidtesttask.data.repository.CovidRepositoryImpl
import com.example.covidtesttask.domain.repository.CovidRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCovidApi(): CovidApi {
        return Retrofit.Builder()
            .baseUrl(Constants.COVID_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(
                GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create()
            ))
            .build()
            .create(CovidApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCovidDb(
        @ApplicationContext appContext: Context
    ): CovidDb {
        return Room.databaseBuilder(
            appContext, CovidDb::class.java, Constants.COVID_DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCovidRepository(
        api: CovidApi,
        db: CovidDb
    ): CovidRepository {
        return CovidRepositoryImpl(api, db)
    }
}