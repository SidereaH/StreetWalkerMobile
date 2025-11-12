@file:OptIn(ExperimentalMaterial3Api::class)

package com.streetwalkermobile.feature.profile.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.streetwalkermobile.feature.profile.viewmodel.ProfileViewModel
import com.streetwalkermobile.shared.ui.components.StreetWalkerTopAppBar
import com.streetwalkermobile.shared.ui.theme.LocalSpacing

@Composable
fun ProfileRoute(
    onBack: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            StreetWalkerTopAppBar(
                title = "Your profile",
                navigationIcon = Icons.Default.ArrowBack,
                onNavigationClick = onBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = spacing.large),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(spacing.large))
            Icon(
                imageVector = Icons.Default.Map,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(spacing.extraLarge * 2)
            )
            Spacer(modifier = Modifier.size(spacing.medium))
            Text(
                text = "Environment: ${state.environment}",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "API base URL: ${state.apiBaseUrl}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Maps key: ${state.mapApiKey}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(spacing.large))
            Icon(
                imageVector = Icons.Default.DarkMode,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = "Dynamic colors follow system theme",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}
