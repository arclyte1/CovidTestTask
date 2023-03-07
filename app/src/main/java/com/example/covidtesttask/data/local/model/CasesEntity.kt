package com.example.covidtesttask.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.covidtesttask.domain.model.Country

@Entity(
    tableName = "cases",
    primaryKeys = ["country_slug", "days_before"],
    foreignKeys = [
        ForeignKey(
            entity = CountryEntity::class,
            parentColumns = ["slug"],
            childColumns = ["country_slug"]
        )
    ]
)
data class CasesEntity(
    @ColumnInfo(name = "country_slug") val countrySlug: String,
    // daysBefore - number of days before update date when this cases were actual
    @ColumnInfo(name = "days_before") val daysBefore: Int,
    @ColumnInfo(name = "total_confirmed") val totalConfirmed: Int,
    @ColumnInfo(name = "new_confirmed") val newConfirmed: Int,
    @ColumnInfo(name = "total_recovered") val totalRecovered: Int,
    @ColumnInfo(name = "new_recovered") val newRecovered: Int,
    @ColumnInfo(name = "total_deaths") val totalDeaths: Int,
    @ColumnInfo(name = "new_deaths") val newDeaths: Int,
) {

    companion object {
        fun fromCountry(country: Country, daysBefore: Int): CasesEntity {
            return CasesEntity(
                countrySlug = country.slug,
                daysBefore = daysBefore,
                totalConfirmed = country.confirmed.total,
                newConfirmed = country.confirmed.new,
                totalRecovered = country.recovered.total,
                newRecovered = country.recovered.new,
                totalDeaths = country.deaths.total,
                newDeaths = country.deaths.new,
            )
        }
    }
}
