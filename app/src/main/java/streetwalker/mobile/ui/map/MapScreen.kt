// ui/map/MapScreen.kt
package streetwalker.mobile.ui.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.compose.ui.platform.LocalLifecycleOwner
import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.Style
import org.maplibre.android.plugins.annotation.SymbolManager
import org.maplibre.android.plugins.annotation.SymbolOptions
import org.maplibre.android.plugins.annotation.Symbol

/**
 * Compose wrapper for MapLibre MapView with SymbolManager usage.
 *
 * Uses MapLibre SDK 12.0.1 and annotation plugin (SymbolManager).
 *
 * styleUrl: URL to your style JSON (example: "http://83.136.235.215:8080/styles/512/street-walker.json")
 */
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    styleUrl: String = "http://83.136.235.215:8080/styles/512/street-walker.json",
    initialLat: Double = 47.2369,
    initialLon: Double = 39.7138,
    markerDrawableRes: Int = android.R.drawable.ic_menu_mylocation, // заменишь на свой
    onMapClickForAdd: (lat: Double, lon: Double) -> Unit = { _, _ -> },
    onSymbolClick: (symbol: Symbol) -> Unit = {}
) {
    val ctx = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // remember MapView to control lifecycle
    val mapView = remember {
        MapView(ctx)
    }

    // Hold refs to MapLibreMap and SymbolManager
    var mapLibreMapRef by remember { mutableStateOf<MapLibreMap?>(null) }
    var symbolManagerRef by remember { mutableStateOf<SymbolManager?>(null) }

    // AndroidView to host MapView
    AndroidView(
        factory = { mapView },
        modifier = modifier,
        update = { view ->
            // nothing to update here: initialization done in getMapAsync below
        }
    )

    // Initialize map, style, register icon, SymbolManager etc.
    LaunchedEffect(mapView) {
        mapView.getMapAsync { maplibreMap ->
            mapLibreMapRef = maplibreMap

            // Move camera to initial position
            try {
                val camera = CameraUpdateFactory.newLatLngZoom(LatLng(initialLat, initialLon), 11.0)
                maplibreMap.moveCamera(camera)
            } catch (_: Throwable) { }

            // Build Style.Builder from URI (works like Java example)
            val builder = Style.Builder().fromUri(styleUrl)

            // setStyle with callback (signature used in MapLibre 12.x)
            maplibreMap.setStyle(builder) { style ->
                // register marker image in style (convert drawable to bitmap)
                try {
                    val bmp = drawableToBitmap(ctx, markerDrawableRes)
                    style.addImage("marker-icon", bmp)
                } catch (_: Throwable) {
                    // ignore if cannot add image (style may already have it)
                }

                // create SymbolManager (pattern from your BulkSymbolActivity)
                try {
                    val symbolManager = SymbolManager(mapView, maplibreMap, style)
//                    symbolManager.is = true
                    symbolManagerRef = symbolManager

                    // click listener for symbols
                    symbolManager.addClickListener { symbol ->
                        onSymbolClick(symbol)
                        true
                    }
                } catch (e: Exception) {
                    // If SymbolManager constructor differs, we'll handle errors here.
                    e.printStackTrace()
                }
            }

            // add map click listener to create a symbol and call the callback
            maplibreMap.addOnMapClickListener { latLng ->
                val lat = latLng.latitude
                val lon = latLng.longitude

                // create symbol if symbolManager ready
                symbolManagerRef?.let { mgr ->
                    val opts = SymbolOptions()
                        .withLatLng(org.maplibre.android.geometry.LatLng(lat, lon))
                        .withIconImage("marker-icon")
                    try {
                        mgr.create(opts)
                    } catch (t: Throwable) {
                        t.printStackTrace()
                    }
                }

                // user-level callback to open AddMarker screen etc.
                onMapClickForAdd(lat, lon)
                true
            }
        }
    }

    // Lifecycle bridging: forward lifecycle events to MapView
    DisposableEffect(lifecycleOwner) {
        val owner = lifecycleOwner
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    // MapView requires a Bundle for onCreate; we can pass null here
                    try { mapView.onCreate(Bundle()) } catch (_: Throwable) {}
                }
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> {
                    // cleanup symbolManager
                    symbolManagerRef?.let { try { it.onDestroy() } catch (_: Throwable) {} }
                    mapView.onDestroy()
                }
                else -> {}
            }
        }
        owner.lifecycle.addObserver(observer)
        onDispose {
            owner.lifecycle.removeObserver(observer)
            // also ensure mapView cleanup
            try {
                symbolManagerRef?.onDestroy()
            } catch (_: Throwable) {}
            try { mapView.onDestroy() } catch (_: Throwable) {}
        }
    }
}

/** helper: convert drawable resource to Bitmap for style.addImage(...) */
private fun drawableToBitmap(context: android.content.Context, resId: Int): Bitmap {
    val drawable: Drawable? = ContextCompat.getDrawable(context, resId)
    val width = (drawable?.intrinsicWidth ?: 1).coerceAtLeast(1)
    val height = (drawable?.intrinsicHeight ?: 1).coerceAtLeast(1)
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable?.setBounds(0, 0, canvas.width, canvas.height)
    drawable?.draw(canvas)
    return bitmap
}
