package com.streetwalkermobile.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.streetwalkermobile.feature.friends.ui.FriendsRoute
import com.streetwalkermobile.feature.friends.viewmodel.FriendsViewModel
import com.streetwalkermobile.shared.ui.theme.StreetWalkerMobileTheme
import org.junit.Rule
import org.junit.Test

class FriendsScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun friends_screen_shows_titles() {
        val vm = FriendsViewModel()

        composeRule.setContent {
            StreetWalkerMobileTheme {
                FriendsRoute(onBack = {}, viewModel = vm)
            }
        }

        composeRule.onNodeWithText("Friends").assertIsDisplayed()
        composeRule.onNodeWithText("Your friends").assertIsDisplayed()
        composeRule.onNodeWithText("Suggested").assertIsDisplayed()
    }
}

