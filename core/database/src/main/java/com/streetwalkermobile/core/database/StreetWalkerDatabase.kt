package com.streetwalkermobile.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.streetwalkermobile.core.database.dao.MarkerDao
import com.streetwalkermobile.core.database.entity.MarkerEntity

@Database(
    entities = [MarkerEntity::class],
    version = 1,
    exportSchema = true
)
abstract class StreetWalkerDatabase : RoomDatabase() {
    abstract fun markerDao(): MarkerDao
}
