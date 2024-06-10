package com.example.dragonsairlines.framework.datasource

import com.example.data.StoredData
import com.example.domain.entities.Flight

class StoredDataImpl: StoredData {

    private var filteredFlights: List<Flight> = mutableListOf()

    override fun getStoredFlights(): List<Flight> = filteredFlights

    override fun updateStoredFlights(flights: List<Flight>) {
        filteredFlights = flights
    }

}