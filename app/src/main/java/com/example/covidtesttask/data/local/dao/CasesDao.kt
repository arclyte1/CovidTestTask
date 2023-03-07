package com.example.covidtesttask.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.covidtesttask.data.local.model.CasesEntity

@Dao
interface CasesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cases: List<CasesEntity>)
}