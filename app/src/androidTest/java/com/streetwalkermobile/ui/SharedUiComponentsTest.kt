package com.streetwalkermobile.ui

import androidx.activity.ComponentActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.streetwalkermobile.shared.ui.components.StreetWalkerFloatingActionButton
import com.streetwalkermobile.shared.ui.components.StreetWalkerTopAppBar
import com.streetwalkermobile.shared.ui.theme.StreetWalkerMobileTheme
import org.junit.Rule
import org.junit.Test

class SharedUiComponentsTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun top_app_bar_and_fab_render() {
        composeRule.setContent {
            StreetWalkerMobileTheme {
                StreetWalkerTopAppBar(title = "Title")
                StreetWalkerFloatingActionButton(onClick = {}, icon = Icons.Default.AccountCircle, contentDescription = "Fab")
            }
        }
        composeRule.onNodeWithText("Title").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Fab").assertIsDisplayed()
    }
}
