package com.example.covidtesttask.domain.usecase

import com.example.covidtesttask.common.Resource
import com.example.covidtesttask.domain.model.Summary
import com.example.covidtesttask.domain.repository.CovidRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import retrofit2.HttpException
import javax.inject.Inject

class GetSummaryUseCase @Inject constructor(
    val repository: CovidRepository
) {

    operator fun invoke() = flow<Resource<Summary>> {
        try {
            emit(Resource.Loading())
            repository.getSummary().onEach { summary ->
                if (summary == null) {
                    emit(Resource.Loading())
                } else {
                    emit(Resource.Success(summary))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}