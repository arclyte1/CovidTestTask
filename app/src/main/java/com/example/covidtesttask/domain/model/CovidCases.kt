package com.example.covidtesttask.domain.model

import java.util.*

data class CovidCases(
    val date: Date? = null,
    val newConfirmed: Int,
    val totalConfirmed: Int,
    val newRecovered: Int,
    val totalRecovered: Int,
    val newDeaths: Int,
    val totalDeaths: Int,
)
