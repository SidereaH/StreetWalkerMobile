package com.streetwalkermobile.feature.map.viewmodel

data class MapUiState(
    val isLoading: Boolean = true,
    val markers: List<MarkerSummary> = emptyList()
)

data class MarkerSummary(
    val id: String,
    val title: String
)
