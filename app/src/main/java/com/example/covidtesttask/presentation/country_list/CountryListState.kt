package com.example.covidtesttask.presentation.country_list

import com.example.covidtesttask.domain.model.Summary
import com.example.covidtesttask.presentation.country_list.model.CountryItem
import java.util.*

data class CountryListState(
    val lastUpdated: Date? = null,
    val countries: List<CountryItem> = emptyList(),
    val searchQuery: String = "",
)
