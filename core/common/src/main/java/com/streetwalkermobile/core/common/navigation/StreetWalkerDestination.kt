package com.streetwalkermobile.core.common.navigation

interface StreetWalkerDestination {
    val route: String
}

object StreetWalkerDestinations {
    const val MAP = "map"
    const val MARKERS = "markers/{markerId}"
    const val PROFILE = "profile"
    const val FRIENDS = "friends"
}

object StreetWalkerDeepLinks {
    const val MARKER = "streetwalker://marker/{markerId}"
}
