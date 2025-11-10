package com.streetwalkermobile.core.network.userapi

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @POST("/auth/signup")
    suspend fun signup(@Body request: SignupRequest): UserDTO

    @POST("/auth/signin")
    suspend fun signin(@Body request: SigninRequest): AuthResponse

    @POST("/auth/refresh")
    suspend fun refresh(@Query("refreshToken") refreshToken: String): AuthResponse

    @POST("/auth/logout")
    suspend fun logout(@Query("refreshToken") refreshToken: String): String
}

