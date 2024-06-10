package com.example.dragonsairlines

import com.example.dragonsairlines.framework.datasource.api.responses.ExchangeRateResponse
import com.example.dragonsairlines.framework.datasource.PricesRepositoryImpl
import com.example.dragonsairlines.framework.datasource.api.ApiClient
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class PricesRepositoryImplTest {

    val apiClient: ApiClient = mockk()
    private lateinit var sut:  PricesRepositoryImpl

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        sut = PricesRepositoryImpl(apiClient)
    }

    @Test
    fun `calculate price by currency`() = runTest {
        coEvery{ apiClient.getCurrencyServices().getCurrencyRate("USD", "EUR") } returns ExchangeRateResponse("EUR", 0.95)
        val result = sut.getConvertedPrice(10.0, "USD", "EUR")

        assertEquals(result, 9.5)
    }
}