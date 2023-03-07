package com.example.covidtesttask.data.local.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.covidtesttask.domain.model.CountryDetails
import com.example.covidtesttask.domain.model.CountrySummary
import com.example.covidtesttask.domain.model.CovidCases

data class CountryWithCases(
    @Embedded val country: CountryEntity,
    @Relation(
        parentColumn = "slug",
        entityColumn = "country_slug"
    )
    val lastCases: CasesEntity,
    @Relation(
        parentColumn = "slug",
        entityColumn = "country_slug"
    )
    val casesHistory: List<CasesEntity>
) {

    fun toCountrySummary(): CountrySummary {
        return CountrySummary(
            name = country.name,
            code = country.code,
            slug = country.slug,
            cases = CovidCases(
                totalConfirmed = lastCases.totalConfirmed,
                newConfirmed = lastCases.newConfirmed,
                totalRecovered = lastCases.totalRecovered,
                newRecovered = lastCases.newRecovered,
                totalDeaths = lastCases.totalDeaths,
                newDeaths = lastCases.newDeaths
            )
        )
    }

    fun toCountryDetails(): CountryDetails {
        return CountryDetails(
            name = country.name,
            code = country.code,
            slug = country.slug,
            latestCases = CovidCases(
                totalConfirmed = lastCases.totalConfirmed,
                newConfirmed = lastCases.newConfirmed,
                totalRecovered = lastCases.totalRecovered,
                newRecovered = lastCases.newRecovered,
                totalDeaths = lastCases.totalDeaths,
                newDeaths = lastCases.newDeaths
            ),
            historyCases = casesHistory.map { it.toCovidCases(country.dateUpdated) }
        )
    }
}
