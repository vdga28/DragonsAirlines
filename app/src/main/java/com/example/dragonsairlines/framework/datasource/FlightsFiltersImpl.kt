package com.example.dragonsairlines.framework.datasource

import com.example.data.FlightsFilters
import com.example.data.repositories.PricesRepository
import com.example.domain.entities.Flight

class FlightsFiltersImpl(private val pricesRepository: PricesRepository): FlightsFilters {

    override suspend fun initialFilteredFlights(flights: List<Flight>, currency: String): List<Flight> {
         return flights.convertPrice(currency)
            .sortByPriceAscending()
            .filterBySameOriginAndDestination()
    }

    override fun filterByPriceRange(
        flights: List<Flight>,
        minPrice: Double,
        maxPrice: Double
    ): List<Flight> {
        return flights.filter { it.price in minPrice..maxPrice }
    }

    private fun List<Flight>.sortByPriceAscending(): List<Flight> {
        return this.sortedBy { it.price }
    }

    private suspend fun  List<Flight>.convertPrice(currencyToConvert: String): List<Flight> {
        val flights = mutableListOf<Flight>()
        this.forEach {
            if (it.currency == currencyToConvert) {
                flights.add(it)
            } else {
                val price = pricesRepository.getConvertedPrice(it.price, it.currency, currencyToConvert)
                val flight = Flight(inbound = it.inbound,
                    outbound = it.outbound,
                    currency = currencyToConvert,
                    price = price)
                flights.add(flight)
            }
        }
        return flights
    }

    private fun  List<Flight>.filterBySameOriginAndDestination(): List<Flight> {
        return  this.distinctBy { it.inbound.origin to
                it.inbound.destination to
                it.outbound.origin to
                it.outbound.origin }
    }
}

