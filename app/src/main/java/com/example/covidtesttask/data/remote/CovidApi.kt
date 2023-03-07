package com.example.covidtesttask.data.remote

import com.example.covidtesttask.data.remote.dto.HistoryDto
import com.example.covidtesttask.data.remote.dto.SummaryDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidApi {

    @GET("summary")
    suspend fun getSummary() : SummaryDto

    @GET("live/country/{slug}/status/confirmed/date/{date}")
    suspend fun getHistory(
        @Path("slug") slug: String,
        @Path("date") date: String
    ): List<HistoryDto>
}