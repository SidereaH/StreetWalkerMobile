package streetwalker.mobile.data.local

import androidx.room.*
import streetwalker.mobile.domain.models.MarkerEntity

@Dao
interface MarkerDao {
    @Query("SELECT * FROM markers ORDER BY createdAt DESC")
    fun getAllFlow(): kotlinx.coroutines.flow.Flow<List<MarkerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(marker: MarkerEntity)

    @Query("SELECT * FROM markers WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): MarkerEntity?

    @Query("SELECT * FROM markers WHERE uploaded = 0")
    suspend fun getPendingUploads(): List<MarkerEntity>

    @Update
    suspend fun update(marker: MarkerEntity)
}
