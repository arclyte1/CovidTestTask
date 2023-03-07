package com.example.covidtesttask.domain.model

data class CountrySummary(
    val name: String,
    val code: String,
    val slug: String,
    val cases: CovidCases
)
