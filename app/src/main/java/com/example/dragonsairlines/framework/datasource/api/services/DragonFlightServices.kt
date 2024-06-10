package com.example.dragonsairlines.framework.datasource.api.services

import com.example.dragonsairlines.framework.datasource.api.responses.FlightsResponse
import retrofit2.http.GET

interface DragonFlightServices {

    @GET(".")
    suspend fun getFlights(): FlightsResponse?
}