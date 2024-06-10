package com.example.data

import com.example.domain.entities.Flight

interface StoredData {
    fun getStoredFlights(): List<Flight>
    fun updateStoredFlights(flights: List<Flight>)
}