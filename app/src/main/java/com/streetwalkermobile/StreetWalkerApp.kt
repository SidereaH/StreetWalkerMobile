package com.streetwalkermobile

import androidx.compose.runtime.Composable
import com.streetwalkermobile.navigation.AppNavHost
import com.streetwalkermobile.navigation.rememberStreetWalkerAppState
import com.streetwalkermobile.shared.ui.theme.StreetWalkerMobileTheme

@Composable
fun StreetWalkerApp() {
    val appState = rememberStreetWalkerAppState()
    val navController = appState.navController

    StreetWalkerMobileTheme {
        AppNavHost(
            navController = navController,
            appState = appState
        )
    }
}
