package com.example.dragonsairlines.framework.datasource.api.responses

import com.google.gson.annotations.SerializedName

data class ExchangeRateResponse(
    val currency: String,
    @SerializedName("exchangeRate")val amount: Double
)