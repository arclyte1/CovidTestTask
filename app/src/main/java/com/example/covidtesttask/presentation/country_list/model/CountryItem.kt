package com.example.covidtesttask.presentation.country_list.model

import com.example.covidtesttask.domain.model.Country

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
        fun fromCountry(country: Country): CountryItem {
            return CountryItem(
                code = country.code,
                displayName = country.name,
                newConfirmed = country.confirmed.new,
                totalConfirmed = country.confirmed.total,
                newRecovered = country.recovered.new,
                totalRecovered = country.recovered.total,
                newDeaths = country.deaths.new,
                totalDeaths = country.deaths.total,
            )
        }
    }
}
