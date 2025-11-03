package streetwalker.mobile.nav

import android.R.attr.type
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import streetwalker.mobile.ui.map.MapScreen
import streetwalker.mobile.ui.marker.addmarker.AddMarkerScreen

@Composable
fun AppNavGraph(startDest: String = "map") {
    val nav = rememberNavController()
    NavHost(nav, startDestination = startDest) {
        composable("map") { MapScreen(styleUrl="http://83.136.235.215:8080/styles/512/street-walker.json",onMapClickForAdd = { lat, lon -> nav.navigate("add?lat=$lat&lon=$lon") }) }
        composable("add?lat={lat}&lon={lon}", arguments = listOf(
            navArgument("lat") { type = NavType.FloatType },
            navArgument("lon") { type = NavType.FloatType }
        )) { backStackEntry ->
            val lat = backStackEntry.arguments?.getFloat("lat")?.toDouble() ?: 0.0
            val lon = backStackEntry.arguments?.getFloat("lon")?.toDouble() ?: 0.0
            AddMarkerScreen(defaultLat = lat, defaultLon = lon, navBack = { nav.popBackStack() })
        }
    }
}
