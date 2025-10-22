package com.streetwalkermobile.app

import android.app.Application
import com.streetwalkermobile.BuildConfig
import com.streetwalkermobile.core.logger.StreetWalkerLogger
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class StreetWalkerApplication : Application() {

    @Inject
    lateinit var logger: StreetWalkerLogger

    override fun onCreate() {
        super.onCreate()
        logger.initialise(debugTree = if (BuildConfig.DEBUG) Timber.DebugTree() else null)
    }
}
