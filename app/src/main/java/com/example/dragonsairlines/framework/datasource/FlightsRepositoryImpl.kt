package com.example.dragonsairlines.framework.datasource

import com.example.data.repositories.FlightsRepository
import com.example.domain.entities.Flight
import com.example.dragonsairlines.framework.datasource.api.ApiClient
import com.example.data.FlightsFilters
import com.example.data.StoredData
import kotlinx.coroutines.*

class FlightsRepositoryImpl(
    private val apiClient: ApiClient,
    private val flightsFilters: FlightsFilters,
    private val storedData: StoredData
): FlightsRepository {

    override suspend fun getFlights(): List<Flight> {
        var flights: List<Flight>
        withContext(Dispatchers.IO) {
            flights = flightsFilters
                .initialFilteredFlights(apiClient.getFlightServices().getFlights()?.flights.orEmpty())
            storedData.updateStoredFlights(flights)
        }
        return flights
    }

    override fun getFlightsByPrice(minPrice: Double, maxPrice: Double): List<Flight> {
        return flightsFilters.filterByPriceRange(storedData.getStoredFlights(), minPrice, maxPrice)
    }
}