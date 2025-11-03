package streetwalker.mobile.ui.marker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import streetwalker.mobile.data.repo.MarkerRepository
import streetwalker.mobile.domain.models.MarkerEntity
import javax.inject.Inject

@HiltViewModel
class MarkerViewModel @Inject constructor(
    private val repo: MarkerRepository
): ViewModel() {
    val markers = repo.markersFlow()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun createMarker(title: String, desc: String?, lat: Double, lon: Double, visibility: String = "PUBLIC") {
        viewModelScope.launch {
            val m = MarkerEntity(
                title = title,
                description = desc,
                latitude = lat,
                longitude = lon,
                authorId = "me",
                visibility = visibility
            )
            repo.addMarker(m)
        }
    }

    fun sync() {
        viewModelScope.launch {
            repo.syncPending()
        }
    }
}
