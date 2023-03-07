package com.example.covidtesttask.presentation.country_details.model

import com.example.covidtesttask.domain.model.CountryDetails

data class Country(
    val countryCode: String,
    val name: String? = null,
    val latestCases: Cases? = null,
    val history: List<Cases> = emptyList(),
) {

    companion object {
        fun fromCountryDetails(countryDetails: CountryDetails): Country {
            return Country(
                countryCode = countryDetails.code,
                name = countryDetails.name,
                latestCases = Cases.fromCovidCases(countryDetails.latestCases),
                history = countryDetails.historyCases.map { Cases.fromCovidCases(it) }
            )
        }
    }
}
