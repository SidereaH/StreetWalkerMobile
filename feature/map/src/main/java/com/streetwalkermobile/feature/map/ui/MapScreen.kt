package com.streetwalkermobile.feature.map.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ListItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.streetwalkermobile.feature.map.viewmodel.MapViewModel
import com.streetwalkermobile.feature.map.viewmodel.MarkerSummary
import com.streetwalkermobile.shared.ui.components.StreetWalkerFilterChip
import com.streetwalkermobile.shared.ui.components.StreetWalkerFloatingActionButton
import com.streetwalkermobile.shared.ui.components.StreetWalkerTopAppBar
import com.streetwalkermobile.shared.ui.theme.LocalSpacing

@Composable
fun MapRoute(
    onMarkerSelected: (String) -> Unit,
    onShowFriends: () -> Unit,
    onShowProfile: () -> Unit,
    viewModel: MapViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            StreetWalkerTopAppBar(title = "Street Walker Map")
        },
        floatingActionButton = {
            StreetWalkerFloatingActionButton(
                onClick = onShowProfile,
                icon = Icons.Default.AccountCircle,
                contentDescription = "Profile"
            )
        }
    ) { paddingValues ->
        if (state.isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Text(
                    text = "Loading markers…",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = spacing.small)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    top = paddingValues.calculateTopPadding() + spacing.small,
                    start = spacing.medium,
                    end = spacing.medium,
                    bottom = paddingValues.calculateBottomPadding() + spacing.extraLarge
                ),
                verticalArrangement = Arrangement.spacedBy(spacing.small)
            ) {
                item {
                    StreetWalkerFilterChip(
                        label = "Friends",
                        selected = false,
                        onClick = onShowFriends
                    )
                }

                items(state.markers) { marker ->
                    MarkerRow(marker = marker, onMarkerSelected = onMarkerSelected)
                }
            }
        }
    }
}

@Composable
private fun MarkerRow(
    marker: MarkerSummary,
    onMarkerSelected: (String) -> Unit
) {
    ListItem(
        headlineContent = {
            Text(
                text = marker.title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        leadingContent = {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = null
            )
        },
        supportingContent = {
            Text(
                text = "Tap to open marker details",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start
            )
        },
        trailingContent = {
            StreetWalkerFloatingActionButton(
                onClick = { onMarkerSelected(marker.id) },
                icon = Icons.Default.Group,
                contentDescription = "View marker"
            )
        }
    )
}
