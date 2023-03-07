package com.example.covidtesttask.presentation.country_list.model

import com.example.covidtesttask.domain.model.CountrySummary

data class CountryItem(
    val code: String,
    val displayName: String,
    val newConfirmed: Int,
    val totalConfirmed: Int,
    val newRecovered: Int,
    val totalRecovered: Int,
    val newDeaths: Int,
    val totalDeaths: Int,
) {

    companion object {
        fun fromCountry(country: CountrySummary): CountryItem {
            return CountryItem(
                code = country.code,
                displayName = country.name,
                newConfirmed = country.cases.newConfirmed,
                totalConfirmed = country.cases.totalConfirmed,
                newRecovered = country.cases.newRecovered,
                totalRecovered = country.cases.totalRecovered,
                newDeaths = country.cases.newDeaths,
                totalDeaths = country.cases.totalDeaths,
            )
        }
    }
}
