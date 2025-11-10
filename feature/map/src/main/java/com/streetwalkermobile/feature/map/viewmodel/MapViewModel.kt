package com.streetwalkermobile.feature.map.viewmodel

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
class MapViewModel @Inject constructor(
    private val markerRepository: MarkerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    init {
        markerRepository.seedMarkers(viewModelScope)
        observeMarkers()
    }

    private fun observeMarkers() {
        viewModelScope.launch {
            markerRepository.markers.collect { markers ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        markers = markers.map { entity ->
                            MarkerSummary(entity.id, entity.title)
                        }
                    )
                }
            }
        }
    }
}
