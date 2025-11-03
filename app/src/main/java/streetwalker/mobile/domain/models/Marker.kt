package streetwalker.mobile.domain.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "markers")
data class MarkerEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val authorId: String,
    val title: String,
    val photoUrl: String,
    val localPhotoUri: String?,
    val description: String?,
    val latitude: Double,
    val longitude: Double,
    val visibility: String, // "PRIVATE"/"FRIENDS"/"PUBLIC"
    val createdAt: Long = System.currentTimeMillis(),
    val uploaded: Boolean = false
)
