package com.streetwalkermobile.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.streetwalkermobile.core.config.Environment
import com.streetwalkermobile.core.config.EnvironmentConfig
import com.streetwalkermobile.feature.profile.ui.ProfileRoute
import com.streetwalkermobile.feature.profile.viewmodel.ProfileViewModel
import com.streetwalkermobile.shared.ui.theme.StreetWalkerMobileTheme
import org.junit.Rule
import org.junit.Test

class ProfileScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun profile_displays_environment_info() {
        val env = EnvironmentConfig(Environment.DEV, "http://example", "MAP_KEY")
        val vm = ProfileViewModel(env)

        composeRule.setContent {
            StreetWalkerMobileTheme {
                ProfileRoute(onBack = {}, viewModel = vm)
            }
        }

        composeRule.onNodeWithText("Your profile").assertIsDisplayed()
        composeRule.onNodeWithText("Environment: DEV").assertIsDisplayed()
        composeRule.onNodeWithText("API base URL: http://example").assertIsDisplayed()
        composeRule.onNodeWithText("Maps key: MAP_KEY").assertIsDisplayed()
    }
}

