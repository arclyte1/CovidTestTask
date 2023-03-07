package com.example.covidtesttask.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.covidtesttask.domain.model.CountrySummary
import com.example.covidtesttask.domain.model.CovidCases
import java.util.*

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

    fun toCovidCases(lastUpdated: Long? = null): CovidCases {
        val date = if (lastUpdated == null) {
            null
        } else {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = lastUpdated - daysBefore.toLong() * 24 * 60 * 60 * 1000
            calendar.time
        }
        return CovidCases(
            date = date,
            totalConfirmed = totalConfirmed,
            newConfirmed = newConfirmed,
            totalRecovered = totalRecovered,
            newRecovered = newRecovered,
            totalDeaths = totalDeaths,
            newDeaths = newDeaths
        )
    }

    companion object {
        fun fromCountry(country: CountrySummary, daysBefore: Int): CasesEntity {
            return CasesEntity(
                countrySlug = country.slug,
                daysBefore = daysBefore,
                totalConfirmed = country.cases.totalConfirmed,
                newConfirmed = country.cases.newConfirmed,
                totalRecovered = country.cases.totalRecovered,
                newRecovered = country.cases.newRecovered,
                totalDeaths = country.cases.totalDeaths,
                newDeaths = country.cases.newDeaths,
            )
        }
    }
}
