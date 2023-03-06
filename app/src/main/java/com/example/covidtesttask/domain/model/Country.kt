package com.example.covidtesttask.domain.model

data class Country(
    val name: String,
    val code: String,
    val slug: String,
    val confirmed: CovidCases,
    val deaths: CovidCases,
    val recovered: CovidCases,
)
