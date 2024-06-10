package com.example.dragonsairlines.framework.datasource.api.util

class URLProviderImpl: URLProvider {

    override fun get(url: DragonURL): String {
        return when (url) {
            DragonURL.FLIGHTS_BASE_URL -> "https://odigeo-testandroid.herokuapp.com"
            DragonURL.CURRENCY_BASE_URL -> "http://jarvisstark.herokuapp.com/"
        }
    }
}