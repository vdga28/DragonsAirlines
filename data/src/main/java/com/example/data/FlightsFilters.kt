package com.example.data

import com.example.domain.Currency
import com.example.domain.entities.Flight

interface FlightsFilters {
    suspend fun initialFilteredFlights(flights: List<Flight>, currency: String = Currency.EUR.name): List<Flight>

    fun filterByPriceRange(flights: List<Flight>, minPrice: Double, maxPrice: Double): List<Flight>
}