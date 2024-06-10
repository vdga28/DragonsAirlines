package com.example.dragonsairlines

import com.example.data.repositories.PricesRepository
import com.example.domain.entities.Flight
import com.example.domain.entities.FlightDetail
import com.example.dragonsairlines.framework.datasource.FlightsFiltersImpl
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.GlobalContext.stopKoin
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class FlightsFiltersImplTest {

    private val pricesRepository: PricesRepository = mockk()
    private val sut = FlightsFiltersImpl(pricesRepository)
    val inboundA = FlightDetail("Drogon",
        airlineImage = "http://dragonimages.net/images/gallery/dragon-images-by-unknown-253.jpg",
        arrivalDate = "1/11/2769",
        arrivalTime = "9:13",
        departureDate = "4/24/276",
        departureTime = "6:41",
        destination = "Norvos",
        origin = "Lys")

    val outboundA = FlightDetail("Viserion",
        airlineImage = "http://dragonimages.net/images/gallery/dragon-images-by-unknown-253.jpg",
        arrivalDate = "2/19/2843",
        arrivalTime = "19:45",
        departureDate = "4/24/276",
        departureTime = "6:41",
        destination = "Lys",
        origin = "Norvos")

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
        stopKoin()
    }

    @Test
    fun `filter by same destination`() = runBlocking {
        val flightA = Flight(inbound = inboundA, outbound = outboundA, price = 30701.3, currency = "EUR")
        val flightB = Flight(inbound = inboundB, outbound = outboundB, price = 30431.3, currency = "EUR")
        val flightC = Flight(inbound = inboundC, outbound = outboundC, price = 24701.3, currency = "EUR")

        val flightsToFilter = listOf(flightA, flightB, flightC)
        val expectedFlights = listOf(flightC, flightB)

        val result = sut.initialFilteredFlights(flightsToFilter, "EUR")

        assertEquals(result, expectedFlights)
    }

    @Test
    fun `flights ordered by price ascending`() = runBlocking {

        val flightA = Flight(inbound = inboundA, outbound = outboundA, price = 30701.3, currency = "EUR")
        val flightB = Flight(inbound = inboundB, outbound = outboundB, price = 30431.3, currency = "EUR")
        val flightC = Flight(inbound = inboundC, outbound = outboundC, price = 24701.3, currency = "EUR")
        val flightD = Flight(inbound = inboundD, outbound = outboundD, price = 701.3, currency = "EUR")
        val flightE = Flight(inbound = inboundB, outbound = outboundD, price = 4701.3, currency = "EUR")

        val flightsToFilter = listOf(flightA, flightB, flightC, flightD, flightE)
        val expectedPricesFlights = listOf(701.3, 4701.3, 24701.3, 30431.3)

        val result = sut.initialFilteredFlights(flightsToFilter, "EUR").map { it.price }

        assertEquals(result, expectedPricesFlights)
    }

    @Test
    fun `filter flights by price`() = runBlocking {

        val flightA = Flight(inbound = inboundA, outbound = outboundA, price = 56701.3, currency = "EUR")
        val flightB = Flight(inbound = inboundB, outbound = outboundB, price = 30431.3, currency = "EUR")
        val flightC = Flight(inbound = inboundC, outbound = outboundC, price = 15701.3, currency = "EUR")
        val flightD = Flight(inbound = inboundD, outbound = outboundD, price = 701.3, currency = "EUR")
        val flightE = Flight(inbound = inboundB, outbound = outboundD, price = 4701.3, currency = "EUR")

        val flightsToFilter = listOf(flightA, flightB, flightC, flightD, flightE)
        val expectedPricesFlights = listOf(flightC, flightE)

        val result = sut.filterByPriceRange(flightsToFilter, 3000.0, 16000.0)

        assertEquals(result, expectedPricesFlights)
    }
}