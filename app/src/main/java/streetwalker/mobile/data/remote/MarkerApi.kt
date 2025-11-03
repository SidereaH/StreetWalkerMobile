package streetwalker.mobile.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import streetwalker.mobile.domain.dto.MarkerCreateDTO

data class MarkerDto(
    val id: String?,
    val title: String,
    val description: String?,
    val lat: Double,
    val lon: Double,
    val visibility: String
)

interface MarkerApi {
    @GET("/api/posts")
    suspend fun listMarkers(@Query("bbox") bbox: String? = null): List<MarkerDto>

    @POST("/api/posts")
    suspend fun create(@Body dto: MarkerDto): Response<MarkerCreateDTO>

    @GET("/api/posts/{id}")
    suspend fun get(@Path("id") id: String): MarkerDto
}
