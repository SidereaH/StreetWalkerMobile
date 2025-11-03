package streetwalker.mobile.data.repo

// data/repo/MarkerRepository.kt
import kotlinx.coroutines.flow.Flow
import streetwalker.mobile.data.local.MarkerDao
import streetwalker.mobile.data.remote.MarkerApi
import streetwalker.mobile.data.remote.MarkerDto
import streetwalker.mobile.domain.models.MarkerEntity
import javax.inject.Inject
import javax.inject.Singleton

interface MarkerRepository {
    fun markersFlow(): Flow<List<MarkerEntity>>
    suspend fun addMarker(marker: MarkerEntity)
    suspend fun syncPending()
}

@Singleton
class MarkerRepositoryImpl @Inject constructor(
    private val dao: MarkerDao,
    private val api: MarkerApi
): MarkerRepository {
    override fun markersFlow(): Flow<List<MarkerEntity>> = dao.getAllFlow()
    override suspend fun addMarker(marker: MarkerEntity) {
        dao.insert(marker.copy(uploaded = false))
    }

    override suspend fun syncPending() {
        val pending = dao.getPendingUploads()
        for (m in pending) {
            try {
                val dto = MarkerDto(m.id, m.title, m.description, m.latitude, m.longitude, m.visibility)
                val resp = api.create(dto)
                if (resp.isSuccessful) {
                    dao.update(m.copy(uploaded = true))
                }
            } catch (e: Exception) {
                // лог и retry позже
            }
        }
    }
}
