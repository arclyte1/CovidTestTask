package com.example.covidtesttask.data.remote.dto

import com.example.covidtesttask.domain.model.Country
import com.example.covidtesttask.domain.model.CovidCases
import com.google.gson.annotations.SerializedName

data class SummaryCountryDto(
    @SerializedName("Country") val name: String,
    @SerializedName("CountryCode") val code: String,
    @SerializedName("Slug") val slug: String,
    @SerializedName("NewConfirmed") val newConfirmed: Int,
    @SerializedName("TotalConfirmed") val totalConfirmed: Int,
    @SerializedName("NewDeaths") val newDeaths: Int,
    @SerializedName("TotalDeaths") val totalDeaths: Int,
    @SerializedName("NewRecovered") val newRecovered: Int,
    @SerializedName("TotalRecovered") val totalRecovered: Int,
) {

    fun toCountry(): Country {
        return Country(
            name = name,
            code = code,
            slug = slug,
            confirmed = CovidCases(
                total = totalConfirmed,
                new = newConfirmed,
            ),
            deaths = CovidCases(
                total = totalDeaths,
                new = newDeaths,
            ),
            recovered = CovidCases(
                total = totalRecovered,
                new = newRecovered,
            ),
        )
    }
}