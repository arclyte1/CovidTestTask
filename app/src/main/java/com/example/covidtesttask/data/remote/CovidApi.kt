package com.example.covidtesttask.data.remote

import com.example.covidtesttask.data.remote.dto.SummaryDto
import retrofit2.http.GET

interface CovidApi {

    @GET("summary")
    suspend fun getSummary() : SummaryDto
}