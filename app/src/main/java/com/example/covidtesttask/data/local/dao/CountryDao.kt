package com.example.covidtesttask.data.local.dao

import androidx.room.*
import com.example.covidtesttask.data.local.model.CountryEntity
import com.example.covidtesttask.data.local.model.CountryWithCases

@Dao
interface CountryDao {

    @Transaction
    @Query("SELECT * FROM country")
    fun getCountriesWithCases(): List<CountryWithCases>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(countries: List<CountryEntity>)
}