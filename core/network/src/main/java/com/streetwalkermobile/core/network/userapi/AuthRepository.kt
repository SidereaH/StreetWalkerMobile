package com.streetwalkermobile.core.network.userapi

import com.streetwalkermobile.core.config.SecureStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val api: AuthApi,
    private val secureStorage: SecureStorage
) {
    suspend fun signup(request: SignupRequest): UserDTO = api.signup(request)

    suspend fun signin(request: SigninRequest): AuthResponse {
        val response = api.signin(request)
        saveTokens(response)
        return response
    }

    suspend fun refresh(refreshToken: String? = null): AuthResponse {
        val token = refreshToken ?: secureStorage.getString(KEY_REFRESH_TOKEN)
            ?: throw IllegalStateException("No refresh token")
        val response = api.refresh(token)
        saveTokens(response)
        return response
    }

    suspend fun logout(): String {
        val refresh = secureStorage.getString(KEY_REFRESH_TOKEN)
            ?: return "Nothing to logout"
        val result = api.logout(refresh)
        clearTokens()
        return result
    }

    suspend fun currentAccessToken(): String? = secureStorage.getString(KEY_ACCESS_TOKEN)
    suspend fun currentRefreshToken(): String? = secureStorage.getString(KEY_REFRESH_TOKEN)

    suspend fun clearTokens() {
        secureStorage.remove(KEY_ACCESS_TOKEN)
        secureStorage.remove(KEY_REFRESH_TOKEN)
    }

    private suspend fun saveTokens(response: AuthResponse) {
        secureStorage.putString(KEY_ACCESS_TOKEN, response.accessToken)
        secureStorage.putString(KEY_REFRESH_TOKEN, response.refreshToken)
    }

    private companion object {
        const val KEY_ACCESS_TOKEN = "access_token"
        const val KEY_REFRESH_TOKEN = "refresh_token"
    }
}

