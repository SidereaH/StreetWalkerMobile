@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.streetwalkermobile.feature.friends.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.streetwalkermobile.feature.friends.viewmodel.FriendsViewModel
import com.streetwalkermobile.shared.ui.components.StreetWalkerFloatingActionButton
import com.streetwalkermobile.shared.ui.components.StreetWalkerTopAppBar
import com.streetwalkermobile.shared.ui.theme.LocalSpacing

@Composable
fun FriendsRoute(
    onBack: () -> Unit,
    viewModel: FriendsViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            StreetWalkerTopAppBar(
                title = "Friends",
                navigationIcon = Icons.Default.ArrowBack,
                onNavigationClick = onBack
            )
        },
        floatingActionButton = {
            StreetWalkerFloatingActionButton(
                onClick = {},
                icon = Icons.Default.PersonAdd,
                contentDescription = "Invite friend"
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = spacing.large),
            verticalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Your friends",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.size(spacing.small))
                    state.friends.forEach { friend ->
                        Text(
                            text = friend,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            item {
                Divider()
            }

            item {
                Text(
                    text = "Suggested",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            items(state.suggested) { suggestion ->
                Text(
                    text = suggestion,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
