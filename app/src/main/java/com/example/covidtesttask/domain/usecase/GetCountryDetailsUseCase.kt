package com.example.covidtesttask.domain.usecase

import com.example.covidtesttask.domain.repository.CovidRepository
import javax.inject.Inject

class GetCountryDetailsUseCase @Inject constructor(
    private val repository: CovidRepository
) {

    operator fun invoke(countryCode: String) = repository.getDetails(countryCode)
}