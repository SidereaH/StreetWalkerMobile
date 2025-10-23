package com.streetwalkermobile.feature.markers.viewmodel

data class MarkerUiState(
    val markerId: String = "",
    val title: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
