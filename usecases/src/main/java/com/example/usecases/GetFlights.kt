package com.example.usecases

import com.example.data.repositories.FlightsRepository
import com.example.data.repositories.PricesRepository
import com.example.domain.entities.Flight
import java.math.RoundingMode

class GetFlights(private val flightsRepository: FlightsRepository) {

    suspend operator fun invoke(): List<Flight> = flightsRepository.getFlights()

}