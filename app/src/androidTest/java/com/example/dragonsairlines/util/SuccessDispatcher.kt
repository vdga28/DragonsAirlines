package com.example.dragonsairlines.util

import android.content.Context
import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry
import com.example.dragonsairlines.util.AssetReaderUtil.asset
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection

class SuccessDispatcher(
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
) : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        val errorResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        val pathWithoutQueryParams = Uri.parse(request.path).path ?: return errorResponse

        val from: String = Uri.parse(request.path).getQueryParameter("from").orEmpty()
        val to = Uri.parse(request.path).getQueryParameter("to").orEmpty()

        val responseFile = when (pathWithoutQueryParams) {
            "/" -> "flights_response.json"
            "/currency" -> getResponseByCurrency(from, to)
            else -> "empty_response.json"
        }

        val responseBody = asset(context, responseFile)
        return MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(responseBody)
    }

    private fun getResponseByCurrency(
        from: String,
        to: String
    ): String {
        return when (to) {
            "EUR" -> when (from) {
                "USD" -> "eur_to_usd.json"
                "JPY" -> "eur_to_jpy.json"
                "GBP" -> "eur_to_gbp.json"
                else -> "default_exchange.json"
            }
            else -> "default_exchange.json"
        }
    }
}
