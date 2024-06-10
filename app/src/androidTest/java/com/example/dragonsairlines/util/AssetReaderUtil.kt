package com.example.dragonsairlines.util

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object AssetReaderUtil {

    @Throws(RuntimeException::class)
    fun asset(context: Context, assetPath: String): String {
        try {
            val inputStream = context.assets.open(assetPath)
            return inputStreamToString(inputStream)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    private fun inputStreamToString(inputStream: InputStream, charsetName: String = "UTF-8"): String {
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, charsetName)
        reader.readLines().forEach {
            builder.append(it)
        }
        reader.close()
        return builder.toString()
    }
}
