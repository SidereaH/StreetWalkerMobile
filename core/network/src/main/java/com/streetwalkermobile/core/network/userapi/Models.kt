package com.streetwalkermobile.core.network.userapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("accessToken") val accessToken: String,
    @SerialName("refreshToken") val refreshToken: String
)

@Serializable
data class SignupRequest(
    val username: String,
    val email: String,
    val phone: String,
    val password: String
)

@Serializable
data class SigninRequest(
    val phone: String,
    val password: String
)

@Serializable
data class UserDTO(
    val username: String,
    val email: String,
    val phone: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val description: String? = null,
    val avatarUrl: String? = null,
    val role: String,
    val status: String
)

@Serializable
data class User(
    val id: Long? = null,
    val username: String,
    val email: String,
    val phone: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val description: String? = null,
    val avatarUrl: String? = null,
    val role: String? = null,
    val status: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class UserCreateDTO(
    val username: String,
    val email: String,
    val phone: String,
    val password: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val description: String? = null,
    val avatarUrl: String? = null
)

@Serializable
data class UserUpdateDTO(
    val username: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val description: String? = null,
    val avatarUrl: String? = null
)

@Serializable
data class PageUsers(
    val content: List<User> = emptyList(),
    val totalElements: Long? = null,
    val totalPages: Int? = null,
    val size: Int? = null,
    val number: Int? = null
)

