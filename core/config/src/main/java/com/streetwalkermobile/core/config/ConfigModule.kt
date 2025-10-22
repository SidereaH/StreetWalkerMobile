package com.streetwalkermobile.core.config

import android.content.Context
import com.streetwalkermobile.core.common.coroutines.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConfigModule {

    @Provides
    @Singleton
    fun provideSecureStorage(
        @ApplicationContext context: Context,
        dispatcherProvider: DispatcherProvider
    ): SecureStorage = EncryptedSecureStorage(context, dispatcherProvider)
}
