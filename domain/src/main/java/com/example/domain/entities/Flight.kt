package com.example.domain.entities

data class Flight(
    val currency: String,
    val inbound: FlightDetail,
    val outbound: FlightDetail,
    val price: Double
)