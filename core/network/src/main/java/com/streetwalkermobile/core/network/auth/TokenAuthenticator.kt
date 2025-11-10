package com.streetwalkermobile.core.network.auth

import com.streetwalkermobile.core.network.userapi.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

@Singleton
class TokenAuthenticator @Inject constructor(
    private val authRepository: AuthRepository
) : Authenticator {

    private val lock = Any()

    override fun authenticate(route: Route?, response: Response): Request? {
        // Don't try to refresh for auth endpoints themselves
        val path = response.request.url.encodedPath
        if (path.startsWith("/auth/")) return null

        // Avoid infinite loops: if we've already attempted with the latest token, give up
        val priorAuth = response.request.header("Authorization")
        val latestAccess = runBlocking { authRepository.currentAccessToken() }
        if (latestAccess != null && priorAuth == "Bearer $latestAccess") {
            return null
        }

        // Refresh tokens in a synchronized block to prevent stampede
        val newAccess = synchronized(lock) {
            runBlocking {
                try {
                    val refreshed = authRepository.refresh()
                    refreshed.accessToken
                } catch (_: Throwable) {
                    null
                }
            }
        } ?: return null

        // Retry the request with the new Authorization header
        return response.request.newBuilder()
            .header("Authorization", "Bearer $newAccess")
            .build()
    }
}
