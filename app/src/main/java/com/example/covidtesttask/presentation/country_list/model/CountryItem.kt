package com.example.covidtesttask.presentation.country_list.model

import com.example.covidtesttask.domain.model.Country

data class CountryItem(
    val code: String,
    val displayName: String,
) {

    companion object {
        fun fromCountry(country: Country): CountryItem {
            return CountryItem(
                code = country.code,
                displayName = country.name,
            )
        }
    }
}
