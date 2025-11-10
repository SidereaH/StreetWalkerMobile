package com.streetwalkermobile.feature.users.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.streetwalkermobile.core.network.userapi.AuthRepository
import com.streetwalkermobile.core.network.userapi.PageUsers
import com.streetwalkermobile.core.network.userapi.SigninRequest
import com.streetwalkermobile.core.network.userapi.SignupRequest
import com.streetwalkermobile.core.network.userapi.User
import com.streetwalkermobile.core.network.userapi.UserCreateDTO
import com.streetwalkermobile.core.network.userapi.UserDTO
import com.streetwalkermobile.core.network.userapi.UserUpdateDTO
import com.streetwalkermobile.core.network.userapi.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UsersApiDemoUiState(
    val output: String = "",
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val isLoading: Boolean = false,
    val snackbar: String? = null
)

@HiltViewModel
class UsersApiDemoViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UsersApiDemoUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    accessToken = authRepository.currentAccessToken(),
                    refreshToken = authRepository.currentRefreshToken()
                )
            }
        }
    }

    fun clearOutput() { _state.update { it.copy(output = "") } }

    fun signup(username: String, email: String, phone: String, password: String) = launchApi {
        val res: UserDTO = authRepository.signup(SignupRequest(username, email, phone, password))
        append("signup => $res")
        snackbar("Успешная регистрация")
    }

    fun signin(phone: String, password: String) = launchApi {
        val res = authRepository.signin(SigninRequest(phone, password))
        _state.update { it.copy(accessToken = res.accessToken, refreshToken = res.refreshToken) }
        append("signin => access=${res.accessToken.take(12)}..., refresh=${res.refreshToken.take(12)}...")
        snackbar("Вход выполнен")
    }

    fun refreshTokens() = launchApi {
        val res = authRepository.refresh()
        _state.update { it.copy(accessToken = res.accessToken, refreshToken = res.refreshToken) }
        append("refresh => access=${res.accessToken.take(12)}...")
        snackbar("Токены обновлены")
    }

    fun logout() = launchApi {
        val msg = authRepository.logout()
        _state.update { it.copy(accessToken = null, refreshToken = null) }
        append("logout => $msg")
        snackbar("Вы вышли из аккаунта")
    }

    fun listUsers(page: Int?, size: Int?, sort: String?) = launchApi {
        val pageRes: PageUsers = usersRepository.list(page, size, sort)
        append("list users => count=${pageRes.content.size}, total=${pageRes.totalElements}")
        snackbar("Загружено: ${pageRes.content.size}")
    }

    fun getUser(id: Long) = launchApi {
        val user: User = usersRepository.getById(id)
        append("get user[$id] => ${user.username} (${user.email})")
        snackbar("Пользователь загружен")
    }

    fun createUser(
        username: String,
        email: String,
        phone: String,
        password: String,
        firstName: String?,
        lastName: String?,
        description: String?,
        avatarUrl: String?
    ) = launchApi {
        val dto = UserCreateDTO(username, email, phone, password, firstName, lastName, description, avatarUrl)
        val res: UserDTO = usersRepository.createUser(dto)
        append("create user => ${res.username} (${res.email})")
        snackbar("Пользователь создан")
    }

    fun updateUser(
        id: Long,
        username: String?,
        email: String?,
        phone: String?,
        firstName: String?,
        lastName: String?,
        description: String?,
        avatarUrl: String?
    ) = launchApi {
        val dto = UserUpdateDTO(username, email, phone, firstName, lastName, description, avatarUrl)
        val res: UserDTO = usersRepository.updateUser(id, dto)
        append("update user[$id] => ${res.username} (${res.email})")
        snackbar("Пользователь обновлён")
    }

    fun search(username: String, page: Int?, size: Int?, sort: String?) = launchApi {
        val pageRes: PageUsers = usersRepository.search(username, page, size, sort)
        append("search '${username}' => ${pageRes.content.size} items")
        snackbar("Найдено: ${pageRes.content.size}")
    }

    private fun launchApi(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true) }
                block()
            } catch (t: Throwable) {
                append("error: ${t.message}")
                snackbar(t.message ?: "Ошибка")
            }
            finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun append(line: String) {
        _state.update { st ->
            val next = if (st.output.isBlank()) line else st.output + "\n" + line
            st.copy(output = next)
        }
    }

    private fun snackbar(text: String) {
        _state.update { it.copy(snackbar = text) }
    }

    fun consumeSnackbar() {
        _state.update { it.copy(snackbar = null) }
    }
}
