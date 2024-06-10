package com.example.domain.entities

data class FlightDetail(
    val airline: String,
    val airlineImage: String,
    val arrivalDate: String,
    val arrivalTime: String,
    val departureDate: String,
    val departureTime: String,
    val destination: String,
    val origin: String
)