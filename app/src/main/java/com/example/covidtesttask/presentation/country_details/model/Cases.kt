package com.example.covidtesttask.presentation.country_details.model

import com.example.covidtesttask.domain.model.CovidCases
import java.util.*

data class Cases(
    val date: Date?,
    val newConfirmed: Int,
    val totalConfirmed: Int,
    val newRecovered: Int,
    val totalRecovered: Int,
    val newDeaths: Int,
    val totalDeaths: Int,
) {

    companion object {
        fun fromCovidCases(covidCases: CovidCases): Cases {
            return Cases(
                date = covidCases.date,
                newConfirmed = covidCases.newConfirmed,
                totalConfirmed = covidCases.totalConfirmed,
                newRecovered = covidCases.newRecovered,
                totalRecovered = covidCases.totalRecovered,
                newDeaths = covidCases.newDeaths,
                totalDeaths = covidCases.totalDeaths
            )
        }
    }
}
