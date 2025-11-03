package streetwalker.mobile.domain.dto


data class MarkerCreateDTO(



    val authorId: Long,
    val title: String,
    val description: String,
    val tags: List<String>,
    val category: CategoryDTO,
    val latitude: Double,
    val longitude: Double,

    val visibility: String, // "PRIVATE"/"FRIENDS"/"PUBLIC"
    val createdAt: Long = System.currentTimeMillis(),
    val uploaded: Boolean = false
)