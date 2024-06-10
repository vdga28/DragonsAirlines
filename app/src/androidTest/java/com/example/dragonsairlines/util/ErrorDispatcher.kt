package com.example.dragonsairlines.util

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection

class ErrorDispatcher : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
    }
}
