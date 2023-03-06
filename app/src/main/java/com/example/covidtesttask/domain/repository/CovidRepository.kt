package com.example.covidtesttask.domain.repository

import com.example.covidtesttask.common.Resource
import com.example.covidtesttask.domain.model.Summary
import kotlinx.coroutines.flow.Flow

interface CovidRepository {

    suspend fun getSummary() : Flow<Summary?>
}