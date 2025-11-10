package com.streetwalkermobile.core.network

import com.streetwalkermobile.core.config.EnvironmentConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.Authenticator

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authenticator: Authenticator
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .authenticator(authenticator)
            .build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit(
        environmentConfig: EnvironmentConfig,
        okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit = Retrofit.Builder()
        .baseUrl(environmentConfig.apiBaseUrl)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory(CONTENT_TYPE))
        .build()

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): com.streetwalkermobile.core.network.userapi.AuthApi =
        retrofit.create(com.streetwalkermobile.core.network.userapi.AuthApi::class.java)

    @Provides
    @Singleton
    fun provideUsersApi(retrofit: Retrofit): com.streetwalkermobile.core.network.userapi.UsersApi =
        retrofit.create(com.streetwalkermobile.core.network.userapi.UsersApi::class.java)

    private val CONTENT_TYPE = "application/json".toMediaType()
}
