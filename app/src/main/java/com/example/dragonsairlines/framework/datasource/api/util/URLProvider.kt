package com.example.dragonsairlines.framework.datasource.api.util

interface URLProvider {
    fun get(url: DragonURL): String
}