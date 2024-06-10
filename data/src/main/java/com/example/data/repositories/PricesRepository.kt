package com.example.data.repositories


interface PricesRepository {
    suspend fun getConvertedPrice(price: Double, currencyOrigin: String, currencyToConvert: String):  Double
}