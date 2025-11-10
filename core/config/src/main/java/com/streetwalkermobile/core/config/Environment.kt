package com.streetwalkermobile.core.config

enum class Environment {
    DEV,
    STAGE,
    PROD
}

data class EnvironmentConfig(
    val environment: Environment,
    val apiBaseUrl: String,
    val mapApiKey: String
)
