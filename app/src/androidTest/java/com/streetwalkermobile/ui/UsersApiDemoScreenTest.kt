package com.streetwalkermobile.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.streetwalkermobile.core.network.userapi.*
import com.streetwalkermobile.feature.users.ui.UsersApiDemoRoute
import com.streetwalkermobile.feature.users.ui.UsersApiDemoViewModel
import com.streetwalkermobile.shared.ui.theme.StreetWalkerMobileTheme
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class UsersApiDemoScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun users_api_demo_updates_output_after_actions() = runBlocking {
        val authRepo = AuthRepository(
            api = object : AuthApi {
                override suspend fun signup(request: SignupRequest): UserDTO =
                    UserDTO(1L, request.username, request.email)

                override suspend fun signin(request: SigninRequest): AuthResponse =
                    AuthResponse(accessToken = "access123", refreshToken = "refresh123")

                override suspend fun refresh(refreshToken: String): AuthResponse =
                    AuthResponse(accessToken = "access456", refreshToken = "refresh456")

                override suspend fun logout(refreshToken: String): String = "OK"
            },
            secureStorage = object : com.streetwalkermobile.core.config.SecureStorage {
                private val map = mutableMapOf<String, String>()
                override suspend fun putString(key: String, value: String) { map[key] = value }
                override suspend fun getString(key: String): String? = map[key]
                override suspend fun remove(key: String) { map.remove(key) }
                override suspend fun clear() { map.clear() }
            }
        )

        val usersRepo = UsersRepository(
            api = object : UsersApi {
                override suspend fun listUsers(authorization: String, page: Int?, size: Int?, sort: String?): PageUsers =
                    PageUsers(content = listOf(User(1, "john", "john@example.com")), totalElements = 1)

                override suspend fun getUser(authorization: String, id: Long): User =
                    User(id, "john", "john@example.com")

                override suspend fun createUser(authorization: String, request: UserCreateDTO): UserDTO =
                    UserDTO(2, request.username, request.email)

                override suspend fun updateUser(authorization: String, id: Long, request: UserUpdateDTO): UserDTO =
                    UserDTO(id, request.username ?: "user$id", request.email ?: "u$id@example.com")

                override suspend fun searchByUsername(authorization: String, username: String, page: Int?, size: Int?, sort: String?): PageUsers =
                    PageUsers(content = emptyList(), totalElements = 0)
            },
            secureStorage = object : com.streetwalkermobile.core.config.SecureStorage {
                override suspend fun putString(key: String, value: String) {}
                override suspend fun getString(key: String): String? = "token"
                override suspend fun remove(key: String) {}
                override suspend fun clear() {}
            }
        )

        val vm = UsersApiDemoViewModel(authRepository = authRepo, usersRepository = usersRepo)

        composeRule.setContent {
            StreetWalkerMobileTheme {
                UsersApiDemoRoute(onBack = {}, viewModel = vm)
            }
        }

        // Trigger actions directly on ViewModel and assert UI output changes
        vm.signin(phone = "123", password = "pwd")
        composeRule.onNodeWithText("User API Demo").assertIsDisplayed()
        composeRule.onNodeWithText("signin =>", substring = true).assertIsDisplayed()

        vm.listUsers(page = null, size = null, sort = null)
        composeRule.onNodeWithText("list users =>", substring = true).assertIsDisplayed()

        vm.createUser("alice", "alice@example.com", "100", "pwd", null, null, null, null)
        composeRule.onNodeWithText("create user =>", substring = true).assertIsDisplayed()
    }
}
