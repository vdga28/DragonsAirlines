package com.example.data.repositories

import com.example.domain.entities.Flight

interface FlightsRepository {
    suspend fun getFlights() : List<Flight>

    fun getFlightsByPrice(minPrice: Double, maxPrice: Double): List<Flight>
}