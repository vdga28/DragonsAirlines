package com.example.dragonsairlines.framework.datasource.api

import android.content.Context
import com.example.dragonsairlines.framework.datasource.api.services.CurrencyServices
import com.example.dragonsairlines.framework.datasource.api.services.DragonFlightServices
import com.example.dragonsairlines.framework.datasource.api.util.DragonURL
import com.example.dragonsairlines.framework.datasource.api.util.NetworkConnectionInterceptor
import com.example.dragonsairlines.framework.datasource.api.util.URLProvider
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClientImpl(private val urlProvider: URLProvider, private  val context: Context) : ApiClient {

    override fun getFlightServices(): DragonFlightServices {

        val retrofit = getRetrofitConfiguration(urlProvider.get(DragonURL.FLIGHTS_BASE_URL))
            .build()
        return retrofit.create(DragonFlightServices::class.java)
    }

    override fun getCurrencyServices(): CurrencyServices {
        val retrofit = getRetrofitConfiguration(urlProvider.get(DragonURL.CURRENCY_BASE_URL))
            .build()
        return retrofit.create(CurrencyServices::class.java)
    }

    private fun getRetrofitConfiguration(baseUrl: String): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getClientConfiguration().build())

    private fun getClientConfiguration(): OkHttpClient.Builder =
        OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain: Interceptor.Chain ->
                val requestBuilder = chain.request().newBuilder()
                chain.proceed(requestBuilder.build())
            }
            .addInterceptor(getServiceLoggingConfiguration())
            .addInterceptor(NetworkConnectionInterceptor(context));

    private fun getServiceLoggingConfiguration(): HttpLoggingInterceptor {
        val serviceLogging = HttpLoggingInterceptor()
        serviceLogging.level = HttpLoggingInterceptor.Level.BODY
        return serviceLogging
    }
}