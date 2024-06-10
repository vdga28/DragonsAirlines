package com.example.dragonsairlines.framework.datasource.api.util

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String
        get() = "No Internet Connection"
}