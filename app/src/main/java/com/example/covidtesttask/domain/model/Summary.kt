package com.example.covidtesttask.domain.model

import java.util.*

data class Summary(
    val lastUpdated: Date,
    val countries: List<Country>
)
