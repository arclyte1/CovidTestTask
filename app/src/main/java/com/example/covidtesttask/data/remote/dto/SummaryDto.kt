package com.example.covidtesttask.data.remote.dto

import com.example.covidtesttask.domain.model.Summary
import com.google.gson.annotations.SerializedName
import java.util.Date

data class SummaryDto(
    @SerializedName("Countries") val countries: List<SummaryCountryDto>,
    @SerializedName("Date") val date: Date,
) {

    fun toSummary(): Summary {
        return Summary(
            lastUpdated = date,
            countries = countries.map { it.toCountrySummary() }
        )
    }
}
