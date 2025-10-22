package com.streetwalkermobile.feature.profile.viewmodel

import androidx.lifecycle.ViewModel
import com.streetwalkermobile.core.config.EnvironmentConfig
import com.streetwalkermobile.core.config.Environment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class ProfileViewModel @Inject constructor(
    environmentConfig: EnvironmentConfig
) : ViewModel() {

    private val _state = MutableStateFlow(
        ProfileUiState(
            environment = environmentConfig.environment,
            apiBaseUrl = environmentConfig.apiBaseUrl,
            mapApiKey = environmentConfig.mapApiKey
        )
    )
    val state: StateFlow<ProfileUiState> = _state.asStateFlow()
}

data class ProfileUiState(
    val environment: Environment,
    val apiBaseUrl: String,
    val mapApiKey: String
)
