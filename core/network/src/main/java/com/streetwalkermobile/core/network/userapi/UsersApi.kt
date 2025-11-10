package com.streetwalkermobile.core.network.userapi

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UsersApi {
    @GET("/users")
    suspend fun listUsers(
        @Header("Authorization") authorization: String,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = null
    ): PageUsers

    @GET("/users/{id}")
    suspend fun getUser(
        @Header("Authorization") authorization: String,
        @Path("id") id: Long
    ): User

    @POST("/users")
    suspend fun createUser(
        @Header("Authorization") authorization: String,
        @Body request: UserCreateDTO
    ): UserDTO

    // Note: controller path is /users/users/{id} per README
    @PUT("/users/users/{id}")
    suspend fun updateUser(
        @Header("Authorization") authorization: String,
        @Path("id") id: Long,
        @Body request: UserUpdateDTO
    ): UserDTO

    @GET("/users/search/username")
    suspend fun searchByUsername(
        @Header("Authorization") authorization: String,
        @Query("username") username: String,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = null
    ): PageUsers
}

