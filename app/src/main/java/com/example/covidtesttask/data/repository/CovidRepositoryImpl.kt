package com.example.covidtesttask.data.repository

import com.example.covidtesttask.common.Resource
import com.example.covidtesttask.data.remote.CovidApi
import com.example.covidtesttask.domain.model.Summary
import com.example.covidtesttask.domain.repository.CovidRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CovidRepositoryImpl @Inject constructor(
    private val covidApi: CovidApi
) : CovidRepository {

    override suspend fun getSummary() = flow {
        emit(null)
        emit(getRemoteSummary())
    }

    private suspend fun getRemoteSummary(): Summary {
        return covidApi.getSummary().toSummary()
    }
}