package com.example.covidtesttask.data.repository

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
            emit(Resource.Loading())
            val remoteSummary = getRemoteSummary()
            saveLocalSummary(remoteSummary)
            emit(Resource.Success(getLocalSummary()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }

    private suspend fun getRemoteSummary(): Summary {
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
            lastUpdated = if (countriesWithCases.isEmpty()) {
                null
            } else {
                Date().also { date ->
                    countriesWithCases.minOfOrNull { it.country.dateUpdated }?.let {
                        date.time = it
                    }
                }
            }
        )
    }

    override fun getDetails(countryCode: String) = flow {
        try {
            emit(Resource.Loading())
            val localCountryDetails = getLocalCountryDetails(countryCode)
            val localSummary = getLocalSummary()
            emit(Resource.Success(localCountryDetails))
            emit(Resource.Loading())
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