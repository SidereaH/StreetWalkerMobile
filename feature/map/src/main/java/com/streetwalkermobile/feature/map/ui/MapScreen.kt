@file:OptIn(ExperimentalMaterial3Api::class)

package com.streetwalkermobile.feature.map.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.streetwalkermobile.feature.map.viewmodel.MapViewModel
import com.streetwalkermobile.shared.ui.components.StreetWalkerFloatingActionButton
import com.streetwalkermobile.shared.ui.components.StreetWalkerTopAppBar
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.Style
import androidx.compose.runtime.remember

private const val DEFAULT_GL_STYLE_URL = "http://83.136.235.215:8080/styles/street-walker/style.json"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapRoute(
    onMarkerSelected: (String) -> Unit,
    onShowFriends: () -> Unit,
    onShowProfile: () -> Unit,
    onShowUsersDemo: () -> Unit = {},
    viewModel: MapViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            StreetWalkerTopAppBar(
                title = "Street Walker Map",
                actions = {
                    IconButton(onClick = onShowUsersDemo) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.Group,
                            contentDescription = "User API"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            StreetWalkerFloatingActionButton(
                onClick = onShowProfile,
                icon = Icons.Default.AccountCircle,
                contentDescription = "Profile"
            )
        }
    ) { innerPadding ->
        MapLibreMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            styleUrl = DEFAULT_GL_STYLE_URL
        )
    }
}

@Composable
private fun MapLibreMap(
    modifier: Modifier = Modifier,
    styleUrl: String
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val mapView = remember {
        MapView(context).apply {
            getMapAsync { map ->
                map.setStyle(Style.Builder().fromUri(styleUrl))
            }
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { mapView }
    )

    DisposableEffect(lifecycleOwner) {
        val observer = object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) { mapView.onStart() }
            override fun onResume(owner: LifecycleOwner) { mapView.onResume() }
            override fun onPause(owner: LifecycleOwner) { mapView.onPause() }
            override fun onStop(owner: LifecycleOwner) { mapView.onStop() }
            override fun onDestroy(owner: LifecycleOwner) { mapView.onDestroy() }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mapView.onDestroy()
        }
    }
}
