package com.streetwalkermobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.NavType
import com.streetwalkermobile.core.common.navigation.StreetWalkerDeepLinks
import com.streetwalkermobile.feature.friends.navigation.FriendsDestination
import com.streetwalkermobile.feature.friends.ui.FriendsRoute
import com.streetwalkermobile.feature.map.navigation.MapDestination
import com.streetwalkermobile.feature.map.ui.MapRoute
import com.streetwalkermobile.feature.markers.navigation.MarkerDestination
import com.streetwalkermobile.feature.markers.ui.MarkerRoute
import com.streetwalkermobile.feature.profile.navigation.ProfileDestination
import com.streetwalkermobile.feature.profile.ui.ProfileRoute
import com.streetwalkermobile.feature.users.navigation.UsersDestination
import com.streetwalkermobile.feature.users.ui.UsersApiDemoRoute

@Composable
fun AppNavHost(
    navController: NavHostController,
    appState: StreetWalkerAppState
) {
    NavHost(
        navController = navController,
        startDestination = appState.startDestination
    ) {
        composable(MapDestination.route) {
            MapRoute(
                onMarkerSelected = { markerId -> appState.navigateToMarker(markerId) },
                onShowFriends = { appState.navigateToFriends() },
                onShowProfile = { appState.navigateToProfile() },
                onShowUsersDemo = { appState.navigateToUsersDemo() }
            )
        }

        composable(
            route = MarkerDestination.route,
            arguments = listOf(navArgument("markerId") { type = NavType.StringType }),
            deepLinks = listOf(navDeepLink { uriPattern = StreetWalkerDeepLinks.MARKER })
        ) {
            MarkerRoute(onBack = { appState.onBack() })
        }

        composable(ProfileDestination.route) {
            ProfileRoute(onBack = { appState.onBack() })
        }

        composable(FriendsDestination.route) {
            FriendsRoute(onBack = { appState.onBack() })
        }

        composable(UsersDestination.route) {
            UsersApiDemoRoute(onBack = { appState.onBack() })
        }
    }
}
