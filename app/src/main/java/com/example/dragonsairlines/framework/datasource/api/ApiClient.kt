package com.example.dragonsairlines.framework.datasource.api

import com.example.dragonsairlines.framework.datasource.api.services.CurrencyServices
import com.example.dragonsairlines.framework.datasource.api.services.DragonFlightServices

interface ApiClient {
    fun getFlightServices() : DragonFlightServices
    fun getCurrencyServices() : CurrencyServices
}