package com.streetwalkermobile.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.streetwalkermobile.core.database.entity.MarkerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarkerDao {

    @Query("SELECT * FROM markers WHERE id = :id")
    suspend fun getMarker(id: String): MarkerEntity?

    @Query("SELECT * FROM markers ORDER BY title ASC")
    fun observeMarkers(): Flow<List<MarkerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMarkers(markers: List<MarkerEntity>)
}
