package com.example.dragonsairlines.util

import com.example.dragonsairlines.framework.datasource.api.util.DragonURL
import com.example.dragonsairlines.framework.datasource.api.util.URLProvider

class URLProviderMock: URLProvider {

    override fun get(url: DragonURL): String {
        return when (url) {
            DragonURL.FLIGHTS_BASE_URL -> "http://localhost:4007"
            DragonURL.CURRENCY_BASE_URL -> "http://localhost:8080"
        }
    }
}