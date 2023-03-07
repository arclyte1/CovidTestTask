package com.example.covidtesttask.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.covidtesttask.domain.model.CountrySummary
import com.example.covidtesttask.domain.model.CovidCases
import java.util.Date


@Entity(tableName = "country")
data class CountryEntity(
    @ColumnInfo(name = "slug") @PrimaryKey val slug: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "date_updated") val dateUpdated: Long,
) {

    companion object {
        fun fromCountry(country: CountrySummary, dateUpdated: Date): CountryEntity {
            return CountryEntity(
                slug = country.slug,
                name = country.name,
                code = country.code,
                dateUpdated = dateUpdated.time
            )
        }
    }
}
