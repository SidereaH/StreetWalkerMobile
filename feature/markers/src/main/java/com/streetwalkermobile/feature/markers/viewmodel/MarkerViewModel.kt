package com.streetwalkermobile.feature.markers.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.streetwalkermobile.core.database.MarkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MarkerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val markerRepository: MarkerRepository
) : ViewModel() {

    private val markerId: String = savedStateHandle.get<String>("markerId").orEmpty()

    private val _state = MutableStateFlow(MarkerUiState(markerId = markerId))
    val state: StateFlow<MarkerUiState> = _state.asStateFlow()

    init {
        loadMarker()
    }

    private fun loadMarker() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            val marker = markerRepository.findMarkerById(markerId)
            if (marker == null) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Marker not found"
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        isLoading = false,
                        title = marker.title,
                        latitude = marker.latitude,
                        longitude = marker.longitude
                    )
                }
            }
        }
    }
}
