package com.streetwalkermobile.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.streetwalkermobile.feature.map.ui.MapRoute
import com.streetwalkermobile.shared.ui.theme.StreetWalkerMobileTheme
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean

class MapScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun map_top_bar_and_actions_are_visible_and_clickable() {
        val profileClicked = AtomicBoolean(false)
        val usersClicked = AtomicBoolean(false)

        composeRule.setContent {
            StreetWalkerMobileTheme {
                MapRoute(
                    onMarkerSelected = {},
                    onShowFriends = {},
                    onShowProfile = { profileClicked.set(true) },
                    onShowUsersDemo = { usersClicked.set(true) }
                )
            }
        }

        composeRule.onNodeWithText("Street Walker Map").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("User API").assertIsDisplayed().performClick()
        composeRule.onNodeWithContentDescription("Profile").assertIsDisplayed().performClick()

        assert(usersClicked.get())
        assert(profileClicked.get())
    }
}

