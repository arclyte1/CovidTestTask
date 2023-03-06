package com.example.covidtesttask.presentation.country_list

import com.example.covidtesttask.domain.model.Summary
import com.example.covidtesttask.presentation.country_list.model.CountryItem
import java.util.*

data class CountryListState(
    val lastUpdated: Date? = null,
    val countries: List<CountryItem> = emptyList()
) {

    companion object {
        fun fromSummary(summary: Summary): CountryListState {
            return CountryListState(
                lastUpdated = summary.lastUpdated,
                countries = summary.countries.map { CountryItem.fromCountry(it) }
            )
        }
    }
}
