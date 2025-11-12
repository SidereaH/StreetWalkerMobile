package com.streetwalkermobile.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.SavedStateHandle
import com.streetwalkermobile.feature.markers.ui.MarkerRoute
import com.streetwalkermobile.feature.markers.viewmodel.MarkerViewModel
import com.streetwalkermobile.core.database.MarkerRepository
import com.streetwalkermobile.core.database.entity.MarkerEntity
import com.streetwalkermobile.shared.ui.theme.StreetWalkerMobileTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class MarkerScreenTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private fun fakeMarkerRepository(): MarkerRepository {
        val dao = object : com.streetwalkermobile.core.database.dao.MarkerDao {
            private val flow = MutableStateFlow(listOf(
                MarkerEntity("1", "Central Park", 40.785091, -73.968285)
            ))
            override fun observeMarkers(): Flow<List<MarkerEntity>> = flow
            override suspend fun upsertMarkers(markers: List<MarkerEntity>) {}
            override suspend fun getMarker(id: String): MarkerEntity? =
                flow.value.firstOrNull { it.id == id }
        }
        val dispatchers = object : com.streetwalkermobile.core.common.coroutines.DispatcherProvider {
            override val io = kotlinx.coroutines.Dispatchers.Unconfined
            override val main = kotlinx.coroutines.Dispatchers.Unconfined
            override val default = kotlinx.coroutines.Dispatchers.Unconfined
        }
        return MarkerRepository(dao, dispatchers)
    }

    @Test
    fun marker_details_rendered() {
        val vm = MarkerViewModel(
            savedStateHandle = SavedStateHandle(mapOf("markerId" to "1")),
            markerRepository = fakeMarkerRepository()
        )

        composeRule.setContent {
            StreetWalkerMobileTheme {
                MarkerRoute(onBack = {}, viewModel = vm)
            }
        }

        composeRule.onNodeWithText("Central Park").assertIsDisplayed()
        composeRule.onNodeWithText("Latitude:", substring = true).assertIsDisplayed()
        composeRule.onNodeWithText("Longitude:", substring = true).assertIsDisplayed()
    }
}
