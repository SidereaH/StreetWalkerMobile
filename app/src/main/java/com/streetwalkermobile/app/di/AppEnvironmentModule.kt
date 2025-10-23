package com.streetwalkermobile.app.di

import com.streetwalkermobile.BuildConfig
import com.streetwalkermobile.core.config.Environment
import com.streetwalkermobile.core.config.EnvironmentConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppEnvironmentModule {

    @Provides
    @Singleton
    fun provideEnvironmentConfig(): EnvironmentConfig {
        val environment = when (BuildConfig.ENVIRONMENT) {
            "DEV" -> Environment.DEV
            "STAGE" -> Environment.STAGE
            else -> Environment.PROD
        }
        return EnvironmentConfig(
            environment = environment,
            apiBaseUrl = BuildConfig.API_BASE_URL,
            mapApiKey = BuildConfig.MAPS_API_KEY
        )
    }
}
