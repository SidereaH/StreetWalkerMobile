package com.streetwalkermobile.feature.users.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.streetwalkermobile.shared.ui.components.StreetWalkerTopAppBar
import com.streetwalkermobile.shared.ui.theme.LocalSpacing

@Composable
fun UsersApiDemoRoute(
    onBack: () -> Unit,
    viewModel: UsersApiDemoViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val scroll = rememberScrollState()
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val snackbar = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            StreetWalkerTopAppBar(
                title = "User API Demo",
                navigationIcon = Icons.Default.ArrowBack,
                onNavigationClick = onBack,
                actions = {
                    IconButton(onClick = { viewModel.clearOutput() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Clear")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbar) }
    ) { padding ->
        if (uiState.isLoading) {
            LinearProgressIndicator(modifier = androidx.compose.ui.Modifier.fillMaxWidth())
        }
        Column(
            modifier = androidx.compose.ui.Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = spacing.large)
                .verticalScroll(scroll),
            verticalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            AuthSection(viewModel, enabled = !uiState.isLoading)
            UsersSection(viewModel, enabled = !uiState.isLoading)
            Text(
                text = uiState.output,
                style = MaterialTheme.typography.bodySmall,
                modifier = androidx.compose.ui.Modifier.fillMaxWidth()
            )
            Spacer(modifier = androidx.compose.ui.Modifier.size(spacing.large))
        }
    }

    LaunchedEffect(uiState.snackbar) {
        val msg = uiState.snackbar
        if (msg != null) {
            snackbar.showSnackbar(message = msg)
            // clear message after showing
            viewModel.consumeSnackbar()
        }
    }
}

@Composable
private fun AuthSection(viewModel: UsersApiDemoViewModel, enabled: Boolean) {
    val spacing = LocalSpacing.current
    var suUsername by remember { mutableStateOf("") }
    var suEmail by remember { mutableStateOf("") }
    var suPhone by remember { mutableStateOf("") }
    var suPassword by remember { mutableStateOf("") }

    var siPhone by remember { mutableStateOf("") }
    var siPassword by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(spacing.small)) {
        Text("/auth — Signup / Signin / Refresh / Logout", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(suUsername, { suUsername = it }, label = { Text("signup.username") })
        OutlinedTextField(suEmail, { suEmail = it }, label = { Text("signup.email") })
        OutlinedTextField(suPhone, { suPhone = it }, label = { Text("signup.phone") })
        OutlinedTextField(suPassword, { suPassword = it }, label = { Text("signup.password") })
        Row(horizontalArrangement = Arrangement.spacedBy(spacing.small)) {
            Button(onClick = { viewModel.signup(suUsername, suEmail, suPhone, suPassword) }, enabled = enabled) { Text("POST /auth/signup") }
        }
        Spacer(modifier = androidx.compose.ui.Modifier.size(spacing.small))
        OutlinedTextField(siPhone, { siPhone = it }, label = { Text("signin.phone") })
        OutlinedTextField(siPassword, { siPassword = it }, label = { Text("signin.password") })
        Row(horizontalArrangement = Arrangement.spacedBy(spacing.small)) {
            Button(onClick = { viewModel.signin(siPhone, siPassword) }, enabled = enabled) { Text("POST /auth/signin") }
            Button(onClick = { viewModel.refreshTokens() }, enabled = enabled) { Text("POST /auth/refresh") }
            Button(onClick = { viewModel.logout() }, enabled = enabled) { Text("POST /auth/logout") }
        }
        val state by viewModel.state.collectAsStateWithLifecycle()
        Text("access: ${state.accessToken ?: "–"}")
        Text("refresh: ${state.refreshToken ?: "–"}")
    }
}

@Composable
private fun UsersSection(viewModel: UsersApiDemoViewModel, enabled: Boolean) {
    val spacing = LocalSpacing.current
    var page by remember { mutableStateOf("0") }
    var size by remember { mutableStateOf("10") }
    var sort by remember { mutableStateOf("username,asc") }

    var userId by remember { mutableStateOf("") }

    var cuUsername by remember { mutableStateOf("") }
    var cuEmail by remember { mutableStateOf("") }
    var cuPhone by remember { mutableStateOf("") }
    var cuPassword by remember { mutableStateOf("") }
    var cuFirst by remember { mutableStateOf("") }
    var cuLast by remember { mutableStateOf("") }
    var cuDesc by remember { mutableStateOf("") }
    var cuAvatar by remember { mutableStateOf("") }

    var uuId by remember { mutableStateOf("") }
    var uuUsername by remember { mutableStateOf("") }
    var uuEmail by remember { mutableStateOf("") }
    var uuPhone by remember { mutableStateOf("") }
    var uuFirst by remember { mutableStateOf("") }
    var uuLast by remember { mutableStateOf("") }
    var uuDesc by remember { mutableStateOf("") }
    var uuAvatar by remember { mutableStateOf("") }

    var search by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(spacing.small)) {
        Text("/users — list / get / create / update / search", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(spacing.small)) {
            OutlinedTextField(page, { page = it }, label = { Text("page") })
            OutlinedTextField(size, { size = it }, label = { Text("size") })
            OutlinedTextField(sort, { sort = it }, label = { Text("sort") })
        }
        Button(onClick = {
            viewModel.listUsers(page.toIntOrNull(), size.toIntOrNull(), sort.ifBlank { null })
        }, enabled = enabled) { Text("GET /users") }

        Spacer(modifier = androidx.compose.ui.Modifier.size(spacing.small))
        OutlinedTextField(userId, { userId = it }, label = { Text("user id") })
        Button(onClick = { userId.toLongOrNull()?.let { viewModel.getUser(it) } }, enabled = enabled) { Text("GET /users/{id}") }

        Spacer(modifier = androidx.compose.ui.Modifier.size(spacing.small))
        Text("Create user")
        OutlinedTextField(cuUsername, { cuUsername = it }, label = { Text("username") })
        OutlinedTextField(cuEmail, { cuEmail = it }, label = { Text("email") })
        OutlinedTextField(cuPhone, { cuPhone = it }, label = { Text("phone") })
        OutlinedTextField(cuPassword, { cuPassword = it }, label = { Text("password") })
        OutlinedTextField(cuFirst, { cuFirst = it }, label = { Text("firstName (opt)") })
        OutlinedTextField(cuLast, { cuLast = it }, label = { Text("lastName (opt)") })
        OutlinedTextField(cuDesc, { cuDesc = it }, label = { Text("description (opt)") })
        OutlinedTextField(cuAvatar, { cuAvatar = it }, label = { Text("avatarUrl (opt)") })
        Button(onClick = {
            viewModel.createUser(
                cuUsername, cuEmail, cuPhone, cuPassword,
                cuFirst.ifBlank { null }, cuLast.ifBlank { null },
                cuDesc.ifBlank { null }, cuAvatar.ifBlank { null }
            )
        }, enabled = enabled) { Text("POST /users") }

        Spacer(modifier = androidx.compose.ui.Modifier.size(spacing.small))
        Text("Update user")
        OutlinedTextField(uuId, { uuId = it }, label = { Text("id") })
        OutlinedTextField(uuUsername, { uuUsername = it }, label = { Text("username (opt)") })
        OutlinedTextField(uuEmail, { uuEmail = it }, label = { Text("email (opt)") })
        OutlinedTextField(uuPhone, { uuPhone = it }, label = { Text("phone (opt)") })
        OutlinedTextField(uuFirst, { uuFirst = it }, label = { Text("firstName (opt)") })
        OutlinedTextField(uuLast, { uuLast = it }, label = { Text("lastName (opt)") })
        OutlinedTextField(uuDesc, { uuDesc = it }, label = { Text("description (opt)") })
        OutlinedTextField(uuAvatar, { uuAvatar = it }, label = { Text("avatarUrl (opt)") })
        Button(onClick = {
            uuId.toLongOrNull()?.let { id ->
                viewModel.updateUser(
                    id,
                    uuUsername.ifBlank { null }, uuEmail.ifBlank { null }, uuPhone.ifBlank { null },
                    uuFirst.ifBlank { null }, uuLast.ifBlank { null }, uuDesc.ifBlank { null },
                    uuAvatar.ifBlank { null }
                )
            }
        }, enabled = enabled) { Text("PUT /users/users/{id}") }

        Spacer(modifier = androidx.compose.ui.Modifier.size(spacing.small))
        OutlinedTextField(search, { search = it }, label = { Text("search username") })
        Button(onClick = {
            viewModel.search(search, page.toIntOrNull(), size.toIntOrNull(), sort.ifBlank { null })
        }, enabled = enabled) { Text("GET /users/search/username") }
    }
}
