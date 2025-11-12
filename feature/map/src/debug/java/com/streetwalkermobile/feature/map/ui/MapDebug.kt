@file:Suppress("unused")

package com.streetwalkermobile.feature.map.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// Debug replacement to avoid MapLibre dependency during instrumented tests.
@Composable
internal fun MapLibreMap(
    modifier: Modifier = Modifier,
    styleUrl: String
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
    )
}

