package com.example.dragonsairlines

import com.example.data.FlightsFilters
import com.example.dragonsairlines.framework.datasource.api.responses.FlightsResponse
import com.example.domain.entities.Flight
import com.example.domain.entities.FlightDetail
import com.example.dragonsairlines.framework.datasource.FlightsRepositoryImpl
import com.example.data.StoredData
import com.example.dragonsairlines.framework.datasource.api.ApiClient
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.GlobalContext
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class FlightsRepositoryImplTest {

    val apiClient: ApiClient = mockk()
    val storedData: StoredData = spyk()
    val flightsFilter: FlightsFilters = spyk()
    val sut = FlightsRepositoryImpl(apiClient, flightsFilter, storedData)

    val inboundB = FlightDetail("Drogon2",
        airlineImage = "http://dragonimages.net/images/gallery/dragon-images-by-unknown-253.jpg",
        arrivalDate = "1/11/2769",
        arrivalTime = "9:13",
        departureDate = "4/24/276",
        departureTime = "6:41",
        destination = "Norvos",
        origin = "Lys")

    val outboundB = FlightDetail("Viserion2",
        airlineImage = "http://dragonimages.net/images/gallery/dragon-images-by-unknown-253.jpg",
        arrivalDate = "2/19/2843",
        arrivalTime = "19:45",
        departureDate = "4/24/276",
        departureTime = "6:41",
        destination = "Lys",
        origin = "Norvos")

    val inboundC = FlightDetail("Drogon3",
        airlineImage = "http://dragonimages.net/images/gallery/dragon-images-by-unknown-253.jpg",
        arrivalDate = "1/11/2769",
        arrivalTime = "9:13",
        departureDate = "4/24/276",
        departureTime = "6:41",
        destination = "King's Landing",
        origin = "Winterfell")

    val outboundC = FlightDetail("Viserion3",
        airlineImage = "http://dragonimages.net/images/gallery/dragon-images-by-unknown-253.jpg",
        arrivalDate = "2/19/2843",
        arrivalTime = "19:45",
        departureDate = "4/24/276",
        departureTime = "6:41",
        destination = "Winterfell",
        origin = "King's Landing")

    val inboundD = FlightDetail("Drogon3",
        airlineImage = "http://dragonimages.net/images/gallery/dragon-images-by-unknown-253.jpg",
        arrivalDate = "1/11/2769",
        arrivalTime = "9:13",
        departureDate = "4/24/276",
        departureTime = "6:41",
        destination = "Madrid",
        origin = "Caracas")

    val outboundD = FlightDetail("Viserion3",
        airlineImage = "http://dragonimages.net/images/gallery/dragon-images-by-unknown-253.jpg",
        arrivalDate = "2/19/2843",
        arrivalTime = "19:45",
        departureDate = "4/24/276",
        departureTime = "6:41",
        destination = "Caracas",
        origin = "Madrid")

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())

    }

    @After
    fun tearDown() {
        GlobalContext.stopKoin()
    }

    @Test
    fun `get flights by price in range`()  {
        val flightB = Flight(inbound = inboundB, outbound = outboundB, price = 23.9, currency = "EUR")
        val flightC = Flight(inbound = inboundC, outbound = outboundC, price = 11.6, currency = "EUR")
        val flightD = Flight(inbound = inboundD, outbound = outboundD, price = 8.9, currency = "EUR")
        val flightE = Flight(inbound = inboundB, outbound = outboundD, price = 112.5, currency = "EUR")

        every { storedData.getStoredFlights() } returns  listOf(flightD, flightE, flightC, flightB)
        every { flightsFilter.filterByPriceRange(any(), 10.5, 100.6) } returns listOf(flightC, flightB)

        val expected = listOf(flightC, flightB)
        val result = sut.getFlightsByPrice(10.5, 100.6)

        assertEquals(expected, result)
    }

    @Test
    fun `get flights by price not in range`()  {
        val flightB = Flight(inbound = inboundB, outbound = outboundB, price = 253.9, currency = "EUR")
        val flightC = Flight(inbound = inboundC, outbound = outboundC, price = 111.6, currency = "EUR")
        val flightD = Flight(inbound = inboundD, outbound = outboundD, price = 8.9, currency = "EUR")
        val flightE = Flight(inbound = inboundB, outbound = outboundD, price = 112.5, currency = "EUR")

        every { storedData.getStoredFlights() } returns listOf(flightD, flightC, flightE, flightB)
        every { flightsFilter.filterByPriceRange(any(), 10.5, 100.6) } returns emptyList()
        val result = sut.getFlightsByPrice(10.5, 100.6)

        assertEquals(listOf(), result)
    }

    @Test
    fun `get flights with items`() = runTest {
        val flightB = Flight(inbound = inboundB, outbound = outboundB, price = 253.9, currency = "EUR")
        val flightC = Flight(inbound = inboundC, outbound = outboundC, price = 111.6, currency = "EUR")
        val flightD = Flight(inbound = inboundD, outbound = outboundD, price = 8.9, currency = "EUR")
        val flightE = Flight(inbound = inboundB, outbound = outboundD, price = 112.5, currency = "EUR")

        coEvery{ apiClient.getFlightServices().getFlights()} returns  FlightsResponse(listOf(flightB, flightC, flightD, flightE))
        coEvery{ flightsFilter.initialFilteredFlights(any(), "EUR")} returns listOf(flightD, flightC, flightE, flightB)

        val result = sut.getFlights()
        val expected = listOf(flightD, flightC, flightE, flightB)

        assertEquals(expected, result)
    }
}