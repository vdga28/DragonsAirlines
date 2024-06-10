package com.example.dragonsairlines

import android.app.Application
import androidx.test.runner.AndroidJUnitRunner
import com.example.dragonsairlines.framework.datasource.api.ApiClient
import com.example.dragonsairlines.framework.datasource.api.util.URLProvider
import com.example.dragonsairlines.util.ApiClientMocked
import com.example.dragonsairlines.util.URLProviderMock
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@Suppress("unused")
class ApiTestRunner : AndroidJUnitRunner() {

    override fun callApplicationOnCreate(app: Application?) {
        super.callApplicationOnCreate(app)
        loadKoinModules(module {
            single<URLProvider> { URLProviderMock() }
            single<ApiClient> { ApiClientMocked(get()) }
        })
    }
}