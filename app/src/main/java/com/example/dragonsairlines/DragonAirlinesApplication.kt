package com.example.dragonsairlines

import android.app.Application
import com.example.dragonsairlines.di.appModule
import com.example.usecases.di.useCasesModule
import di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class DragonAirlinesApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger(Level.ERROR)
            androidContext(this@DragonAirlinesApplication)
            modules(appModule, dataModule, useCasesModule)
        }
    }
}