package com.example.covidtesttask.data.remote.dto

import com.example.covidtesttask.domain.model.CountrySummary
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

    fun toCountrySummary(): CountrySummary {
        return CountrySummary(
            name = name,
            code = code,
            slug = slug,
            cases = CovidCases(
                totalConfirmed = totalConfirmed,
                newConfirmed = newConfirmed,
                totalRecovered = totalRecovered,
                newRecovered = newRecovered,
                totalDeaths = totalDeaths,
                newDeaths = newDeaths
            )
        )
    }
}