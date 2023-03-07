package com.example.covidtesttask.domain.usecase

import com.example.covidtesttask.domain.repository.CovidRepository
import javax.inject.Inject

class GetSummaryUseCase @Inject constructor(
    private val repository: CovidRepository
) {

    operator fun invoke() = repository.getSummary()
}