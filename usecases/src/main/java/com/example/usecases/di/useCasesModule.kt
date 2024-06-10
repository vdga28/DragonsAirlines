package com.example.usecases.di

import com.example.usecases.*
import org.koin.dsl.module

val useCasesModule = module {
    factory { GetFlightsByPrice(get()) }
    factory { GetFlights(get()) }
}