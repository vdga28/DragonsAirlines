package com.example.dragonsairlines

import com.example.dragonsairlines.util.ErrorDispatcher
import com.example.dragonsairlines.util.SuccessDispatcher
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

open class BaseUiTest {

    private lateinit var webServer: MockWebServer
    private lateinit var webServer2: MockWebServer

    @Before
    @Throws(Exception::class)
    fun server() {
        webServer = MockWebServer()
        webServer2 = MockWebServer()
        webServer.start(4007)
        webServer2.start(8080)
        webServer.dispatcher = SuccessDispatcher()
        webServer2.dispatcher = SuccessDispatcher()
    }

    @After
    fun tearDown() {
        webServer.shutdown()
        webServer2.shutdown()
    }

    fun setErrorDispatcher() {
        webServer.dispatcher = ErrorDispatcher()
        webServer2.dispatcher = ErrorDispatcher()
    }
}