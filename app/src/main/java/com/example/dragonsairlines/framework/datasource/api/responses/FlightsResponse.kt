package com.example.dragonsairlines.framework.datasource.api.responses

import com.example.domain.entities.Flight
import com.google.gson.annotations.SerializedName

data class FlightsResponse(
    @SerializedName("results") val flights: List<Flight>
)