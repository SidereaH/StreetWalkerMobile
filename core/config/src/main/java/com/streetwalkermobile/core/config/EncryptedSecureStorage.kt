package com.streetwalkermobile.core.config

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.streetwalkermobile.core.common.coroutines.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncryptedSecureStorage @Inject constructor(
    context: Context,
    private val dispatchers: DispatcherProvider
) : SecureStorage {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        PREFS_FILE_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override suspend fun putString(key: String, value: String) {
        withContext(dispatchers.io) {
            prefs.edit().putString(key, value).apply()
        }
    }

    override suspend fun getString(key: String): String? = withContext(dispatchers.io) {
        prefs.getString(key, null)
    }

    override suspend fun remove(key: String) {
        withContext(dispatchers.io) {
            prefs.edit().remove(key).apply()
        }
    }

    private companion object {
        const val PREFS_FILE_NAME = "streetwalker_secure_storage"
    }
}
