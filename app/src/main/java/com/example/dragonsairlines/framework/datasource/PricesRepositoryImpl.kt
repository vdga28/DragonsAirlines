package com.example.dragonsairlines.framework.datasource

import com.example.data.repositories.PricesRepository
import com.example.data.toTwoDigitsDouble
import com.example.dragonsairlines.framework.datasource.api.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PricesRepositoryImpl(private val apiClient: ApiClient): PricesRepository {

    private var currencyRateMap = mutableMapOf<String, Double>()

    private val DEFAULT_RATE = 1.0

    override suspend fun getConvertedPrice(
        price: Double,
        currencyOrigin: String,
        currencyToConvert: String
    ): Double {
        return (price * getExchangeRate(currencyOrigin, currencyToConvert)).toTwoDigitsDouble()
    }
    private suspend fun getExchangeRate(currencyOrigin: String, currencyToConvert: String) = withContext(Dispatchers.IO) {
        if (!currencyRateMap.containsKey(currencyOrigin)) {
            getRateByCurrency(currencyOrigin, currencyToConvert)
        } else {
            currencyRateMap.filterKeys { it == currencyOrigin }.values.first()
        }
    }

    private suspend fun getRateByCurrency(currencyOrigin: String, currencyToConvert: String): Double {
        var rate = DEFAULT_RATE
        if (currencyOrigin != currencyToConvert){
            val rateResponse = apiClient.getCurrencyServices().getCurrencyRate(currencyOrigin, currencyToConvert)
            rateResponse?.let {
                rate = it.amount
            }
        }
        currencyRateMap[currencyOrigin] = rate
        return  rate
    }
}