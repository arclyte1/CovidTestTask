package com.example.covidtesttask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.covidtesttask.data.local.dao.CasesDao
import com.example.covidtesttask.data.local.dao.CountryDao
import com.example.covidtesttask.data.local.model.CasesEntity
import com.example.covidtesttask.data.local.model.CountryEntity

@Database(
    entities = [
        CountryEntity::class,
        CasesEntity::class
    ],
    version = 1
)
abstract class CovidDb : RoomDatabase() {

    abstract fun casesDao(): CasesDao
    abstract fun countriesDao(): CountryDao
}