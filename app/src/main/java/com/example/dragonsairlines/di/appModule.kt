package com.example.dragonsairlines.di

import com.example.data.repositories.FlightsRepository
import com.example.data.repositories.PricesRepository
import com.example.dragonsairlines.framework.datasource.api.ApiClient
import com.example.dragonsairlines.framework.datasource.api.ApiClientImpl
import com.example.dragonsairlines.ui.FlightsViewModel
import com.example.data.FlightsFilters
import com.example.data.StoredData
import com.example.dragonsairlines.framework.datasource.*
import com.example.dragonsairlines.framework.datasource.api.util.URLProvider
import com.example.dragonsairlines.framework.datasource.api.util.URLProviderImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    viewModel { FlightsViewModel(get(), get()) }
    factory<FlightsFilters> { FlightsFiltersImpl(get()) }
    factory <FlightsRepository> { FlightsRepositoryImpl(get(), get(), get()) }
    single<PricesRepository> { PricesRepositoryImpl(get()) }
    factory<ApiClient> { ApiClientImpl(get(), get()) }
    single<URLProvider> { URLProviderImpl() }
    single<StoredData> { StoredDataImpl() }
}