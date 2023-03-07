package com.example.covidtesttask.data.local.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.covidtesttask.domain.model.Country
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

    fun toCountry(): Country {
        return Country(
            name = country.name,
            code = country.code,
            slug = country.slug,
            confirmed = CovidCases(
                total = lastCases.totalConfirmed,
                new = lastCases.newConfirmed
            ),
            recovered = CovidCases(
                total = lastCases.totalRecovered,
                new = lastCases.newRecovered
            ),
            deaths = CovidCases(
                total = lastCases.totalDeaths,
                new = lastCases.newDeaths
            )
        )
    }
}
