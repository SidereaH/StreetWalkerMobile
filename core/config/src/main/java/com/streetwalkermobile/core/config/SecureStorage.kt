package com.streetwalkermobile.core.config

interface SecureStorage {
    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String): String?
    suspend fun remove(key: String)
}
