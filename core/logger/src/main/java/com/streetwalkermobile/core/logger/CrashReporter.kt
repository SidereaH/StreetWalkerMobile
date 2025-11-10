package com.streetwalkermobile.core.logger

import timber.log.Timber

interface CrashReporter {
    fun tree(): Timber.Tree
}

class NoOpCrashReporter : CrashReporter {
    override fun tree(): Timber.Tree = object : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            // No-op crash reporting fallback.
        }
    }
}
