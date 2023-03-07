package com.example.covidtesttask.data.repository

import androidx.compose.ui.unit.Constraints
import com.example.covidtesttask.common.Constants
import com.example.covidtesttask.common.Resource
import com.example.covidtesttask.data.local.CovidDb
import com.example.covidtesttask.data.local.model.CasesEntity
import com.example.covidtesttask.data.local.model.CountryEntity
import com.example.covidtesttask.data.remote.CovidApi
import com.example.covidtesttask.data.remote.dto.HistoryDto
import com.example.covidtesttask.domain.model.CountryDetails
import com.example.covidtesttask.domain.model.Summary
import com.example.covidtesttask.domain.repository.CovidRepository
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CovidRepositoryImpl @Inject constructor(
    private val covidApi: CovidApi,
    private val covidDb: CovidDb,
) : CovidRepository {

    override fun getSummary() = flow {
        try {
            emit(Resource.Loading())
            val localSummary = getLocalSummary()
            emit(Resource.Success(localSummary))
            val remoteSummary = getRemoteSummary()
            saveLocalSummary(remoteSummary)
            emit(Resource.Success(getLocalSummary()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }

    private suspend fun getRemoteSummary(): Summary {
//
//        // TESTING
//        delay(500)
//        return Summary(
//            lastUpdated = Calendar.getInstance().time,
//            countries = listOf(
//                Country(
//                    name = "Bangladesh",
//                    code = "BD",
//                    slug = "bangladesh",
//                    confirmed = CovidCases(
//                        total = 700_000_000,
//                        new = 19_000
//                    ),
//                    recovered = CovidCases(
//                        total = 30,
//                        new = 4
//                    ),
//                    deaths = CovidCases(
//                        total = 8,
//                        new = 2
//                    )
//                ),
//                Country(
//                    name = "Bangladesh",
//                    code = "BD",
//                    slug = "bangladesh",
//                    confirmed = CovidCases(
//                        total = 70,
//                        new = 9
//                    ),
//                    recovered = CovidCases(
//                        total = 30,
//                        new = 4
//                    ),
//                    deaths = CovidCases(
//                        total = 8,
//                        new = 2
//                    )
//                ),
//                Country(
//                    name = "Bangladesh",
//                    code = "BD",
//                    slug = "bangladesh",
//                    confirmed = CovidCases(
//                        total = 70,
//                        new = 9
//                    ),
//                    recovered = CovidCases(
//                        total = 30,
//                        new = 4
//                    ),
//                    deaths = CovidCases(
//                        total = 8,
//                        new = 2
//                    )
//                ),
//            )
//        )
//
////        API 503(((
        return covidApi.getSummary().toSummary()
    }

    private fun saveLocalSummary(summary: Summary) {
        covidDb.countriesDao().insertAll(
            summary.countries.map { country ->
                CountryEntity.fromCountry(
                    country = country,
                    dateUpdated = summary.lastUpdated ?: Calendar.getInstance().time
                )
            }
        )
        summary.countries.forEach { country ->
            covidDb.casesDao().insertAll(
                listOf(
                    CasesEntity.fromCountry(country, 0)
                )
            )
        }
    }

    private fun getLocalSummary(): Summary {
        val countriesWithCases = covidDb.countriesDao().getCountriesWithCases()
        return Summary(
            countries = countriesWithCases.map { it.toCountrySummary() },
            lastUpdated = Date().also { date ->
                date.time = countriesWithCases.minOfOrNull { it.country.dateUpdated } ?: 0
            }
        )
    }

    override fun getDetails(countryCode: String) = flow {
        try {
            emit(Resource.Loading())
            val localCountryDetails = getLocalCountryDetails(countryCode)
            val localSummary = getLocalSummary()
            emit(Resource.Success(localCountryDetails))
            localSummary.lastUpdated?.let { lastUpdated ->
                val remoteHistory = getRemoteCasesHistory(localCountryDetails, lastUpdated)
                saveLocalCases(remoteHistory)
                emit(Resource.Success(getLocalCountryDetails(countryCode)))
            }
            Unit
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }

    private fun getLocalCountryDetails(countryCode: String): CountryDetails {
        val countryWithCases = covidDb.countriesDao().getCountryWithCasesByCode(countryCode)
        return countryWithCases.toCountryDetails()
    }

    private suspend fun getRemoteCasesHistory(
        countryDetails: CountryDetails,
        lastUpdated: Date
    ): List<CasesEntity> {
        val calendar = Calendar.getInstance()
        calendar.time = lastUpdated
        calendar.timeInMillis -= Constants.HISTORY_DAYS_COUNT.toLong() + 1 * 24 * 60 * 60 * 1000
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'", Locale.US)
        val history = covidApi.getHistory(
            slug = countryDetails.slug,
            date = dateFormat.format(calendar.time)
        )
        return HistoryDto.historyListToCasesEntities(
            historyList = history,
            lastCases = countryDetails.latestCases,
            lastUpdated = lastUpdated,
            slug = countryDetails.slug
        )
    }

    private fun saveLocalCases(cases: List<CasesEntity>) {
        covidDb.casesDao().insertAll(cases)
    }
}