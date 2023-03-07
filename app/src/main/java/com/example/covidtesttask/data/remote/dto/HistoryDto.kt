package com.example.covidtesttask.data.remote.dto

import com.example.covidtesttask.common.Constants
import com.example.covidtesttask.data.local.model.CasesEntity
import com.example.covidtesttask.domain.model.CovidCases
import com.google.gson.annotations.SerializedName
import java.util.Date

data class HistoryDto(
    @SerializedName("CountryCode") val countryCode: String,
    @SerializedName("Confirmed") val confirmed: Int,
    @SerializedName("Deaths") val deaths: Int,
    @SerializedName("Recovered") val recovered: Int,
    @SerializedName("Date") val date: Date,
) {

    companion object {
        fun historyListToCasesEntities(
            historyList: List<HistoryDto>,
            lastCases: CovidCases,
            lastUpdated: Date,
            slug: String
        ): List<CasesEntity> {
            data class HistoryData(
                var confirmed: Int = 0,
                var recovered: Int = 0,
                var deaths: Int = 0,
            )

            val cases = mutableListOf<CasesEntity>()
            val dates = historyList.map { it.date }.toSet()
            val allData = mutableMapOf<Date, HistoryData>()
            dates.forEach { date ->
                val data = HistoryData()
                historyList.filter { it.date == date }.forEach { historyDto ->
                    data.confirmed += historyDto.confirmed
                    data.recovered += historyDto.recovered
                    data.deaths += historyDto.deaths
                }
                allData[date] = data
            }
            repeat(Constants.HISTORY_DAYS_COUNT + 1) { i ->
                val lastDate = allData.keys.minOfOrNull {
                    val searchingTime = lastUpdated.time - (i + 1).toLong() * 24 * 60 * 60 * 1000
                    if (searchingTime <= it.time)
                        searchingTime - it.time
                    else
                        Long.MAX_VALUE
                }
                val data = if (lastDate != null) {
                    val date = Date()
                    date.time = lastDate
                    allData[date]
                } else {
                    HistoryData(
                        confirmed = lastCases.totalConfirmed,
                        recovered = lastCases.totalRecovered,
                        deaths = lastCases.totalDeaths,
                    )
                }
                cases.add(
                    CasesEntity(
                        countrySlug = slug,
                        daysBefore = i + 1,
                        totalConfirmed = data!!.confirmed,
                        newConfirmed = -1,
                        totalRecovered = data.recovered,
                        newRecovered = -1,
                        totalDeaths = data.deaths,
                        newDeaths = -1,
                    )
                )
            }
            repeat(Constants.HISTORY_DAYS_COUNT) { i ->
                cases[i] = cases[i].copy(
                    newConfirmed = cases[i].totalConfirmed - cases[i+1].totalConfirmed,
                    newRecovered = cases[i].totalRecovered - cases[i+1].totalRecovered,
                    newDeaths = cases[i].totalDeaths - cases[i+1].totalDeaths,
                )
            }
            cases.removeLast()
            return cases.toList()
        }
    }
}