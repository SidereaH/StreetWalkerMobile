package com.streetwalkermobile.core.database

import com.streetwalkermobile.core.common.coroutines.DispatcherProvider
import com.streetwalkermobile.core.database.dao.MarkerDao
import com.streetwalkermobile.core.database.entity.MarkerEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Singleton
class MarkerRepository @Inject constructor(
    private val markerDao: MarkerDao,
    private val dispatchers: DispatcherProvider
) {

    val markers: Flow<List<MarkerEntity>> = markerDao.observeMarkers()

    suspend fun findMarkerById(id: String): MarkerEntity? = withContext(dispatchers.io) {
        markerDao.getMarker(id)
    }

    fun seedMarkers(scope: CoroutineScope) {
        scope.launch(dispatchers.io) {
            val currentMarkers = markerDao.observeMarkers().firstOrNull()
            if (currentMarkers.isNullOrEmpty()) {
                markerDao.upsertMarkers(DEFAULT_MARKERS)
            }
        }
    }

    private companion object {
        private val DEFAULT_MARKERS = listOf(
            MarkerEntity("1", "Central Park", 40.785091, -73.968285),
            MarkerEntity("2", "Brooklyn Bridge", 40.706086, -73.996864),
            MarkerEntity("3", "Times Square", 40.758896, -73.98513)
        )
    }
}
