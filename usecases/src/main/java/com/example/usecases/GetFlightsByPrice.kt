package com.example.usecases

import com.example.data.repositories.FlightsRepository
import com.example.domain.entities.Flight

class GetFlightsByPrice(private val flightsRepository: FlightsRepository) {

    operator fun invoke(minPrice: Double, maxPrice: Double): List<Flight> =
        flightsRepository.getFlightsByPrice(minPrice, maxPrice)

}