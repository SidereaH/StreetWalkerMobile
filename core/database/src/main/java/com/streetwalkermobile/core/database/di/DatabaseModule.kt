package com.streetwalkermobile.core.database.di

import android.content.Context
import androidx.room.Room
import com.streetwalkermobile.core.common.coroutines.DispatcherProvider
import com.streetwalkermobile.core.database.MarkerRepository
import com.streetwalkermobile.core.database.StreetWalkerDatabase
import com.streetwalkermobile.core.database.dao.MarkerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): StreetWalkerDatabase = Room.databaseBuilder(
        context,
        StreetWalkerDatabase::class.java,
        "streetwalker.db"
    ).build()

    @Provides
    fun provideMarkerDao(database: StreetWalkerDatabase): MarkerDao = database.markerDao()

    @Provides
    @Singleton
    fun provideMarkerRepository(
        markerDao: MarkerDao,
        dispatcherProvider: DispatcherProvider
    ): MarkerRepository = MarkerRepository(markerDao, dispatcherProvider)
}
