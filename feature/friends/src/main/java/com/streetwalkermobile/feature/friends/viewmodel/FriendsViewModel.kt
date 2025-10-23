package com.streetwalkermobile.feature.friends.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class FriendsViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(
        FriendsUiState(
            friends = listOf("Alex", "Marina", "Igor", "Svetlana", "Pavel"),
            suggested = listOf("Olga", "Victor", "Julia")
        )
    )
    val state: StateFlow<FriendsUiState> = _state.asStateFlow()
}

data class FriendsUiState(
    val friends: List<String>,
    val suggested: List<String>
)
