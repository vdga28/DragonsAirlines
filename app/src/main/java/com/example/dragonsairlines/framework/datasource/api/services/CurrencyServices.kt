package com.example.dragonsairlines.framework.datasource.api.services

import com.example.dragonsairlines.framework.datasource.api.responses.ExchangeRateResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyServices {

    @GET("currency")
    suspend fun getCurrencyRate(@Query ("from") from: String,
                        @Query ("to") to: String): ExchangeRateResponse?
}