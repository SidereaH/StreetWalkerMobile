package com.streetwalkermobile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.streetwalkermobile.core.common.navigation.StreetWalkerDestinations
import com.streetwalkermobile.core.common.navigation.markerRoute

@Stable
class StreetWalkerAppState internal constructor(
    val navController: NavHostController
) {

    val startDestination: String = StreetWalkerDestinations.MAP

    fun navigateToMarker(markerId: String) {
        navController.navigate(markerRoute(markerId))
    }

    fun navigateToFriends() {
        navController.navigate(StreetWalkerDestinations.FRIENDS)
    }

    fun navigateToProfile() {
        navController.navigate(StreetWalkerDestinations.PROFILE)
    }

    fun onBack() {
        if (!navController.popBackStack()) {
            navController.navigate(startDestination)
        }
    }
}

@Composable
fun rememberStreetWalkerAppState(
    navController: NavHostController = rememberNavController()
): StreetWalkerAppState = remember(navController) {
    StreetWalkerAppState(navController)
}
