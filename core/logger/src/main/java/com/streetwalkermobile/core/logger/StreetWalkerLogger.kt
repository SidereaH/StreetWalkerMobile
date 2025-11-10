package com.streetwalkermobile.core.logger

import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StreetWalkerLogger @Inject constructor(
    private val crashReporter: CrashReporter
) {

    fun initialise(debugTree: Timber.Tree? = Timber.DebugTree()) {
        Timber.uprootAll()
        debugTree?.let(Timber::plant)
        Timber.plant(crashReporter.tree())
    }
}
