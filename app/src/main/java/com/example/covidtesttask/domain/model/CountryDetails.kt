package com.example.covidtesttask.domain.model

data class CountryDetails(
    val name: String,
    val code: String,
    val slug: String,
    val latestCases: CovidCases,
    val historyCases: List<CovidCases>
)
