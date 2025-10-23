package com.streetwalkermobile.core.common.navigation

fun markerRoute(markerId: String): String = StreetWalkerDestinations.MARKERS.replace("{markerId}", markerId)
