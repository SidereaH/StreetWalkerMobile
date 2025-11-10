package com.streetwalkermobile.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "markers")
data class MarkerEntity(
    @PrimaryKey val id: String,
    val title: String,
    val latitude: Double,
    val longitude: Double
)
