package com.streetwalkermobile.core.network.userapi

import com.streetwalkermobile.core.config.SecureStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(
    private val api: UsersApi,
    private val secureStorage: SecureStorage
) {
    private suspend fun bearer(): String =
        secureStorage.getString(KEY_ACCESS_TOKEN)?.let { "Bearer $it" }
            ?: throw IllegalStateException("No access token. Sign in first.")

    suspend fun list(page: Int?, size: Int?, sort: String?): PageUsers =
        api.listUsers(bearer(), page, size, sort)

    suspend fun getById(id: Long): User = api.getUser(bearer(), id)

    suspend fun createUser(req: UserCreateDTO): UserDTO = api.createUser(bearer(), req)

    suspend fun updateUser(id: Long, req: UserUpdateDTO): UserDTO = api.updateUser(bearer(), id, req)

    suspend fun search(username: String, page: Int?, size: Int?, sort: String?): PageUsers =
        api.searchByUsername(bearer(), username, page, size, sort)

    private companion object {
        const val KEY_ACCESS_TOKEN = "access_token"
    }
}

