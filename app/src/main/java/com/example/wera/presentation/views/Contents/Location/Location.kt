package com.example.wera.presentation.views.Contents.Location

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@Composable
fun location(){
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val mapView = MapView(context)
            mapView.onCreate(null)
            mapView.getMapAsync { googleMap ->
                // Initialize and configure the Google Map
                onMapReady(googleMap)
            }
            mapView
        }
    )
}

private fun onMapReady(googleMap: GoogleMap) {
    // Configure the Google Map here
    googleMap.uiSettings.isZoomControlsEnabled = true
    googleMap.uiSettings.isMyLocationButtonEnabled = true

    // Example: Add a marker
    val markerOptions = MarkerOptions()
        .position(LatLng(100.0, 200.0))
        .title("Marker Title")
        .snippet("Marker Description")
    googleMap.addMarker(markerOptions)

    // Example: Move camera to a specific location
    val cameraPosition = CameraUpdateFactory.newLatLngZoom(LatLng(110.0, 4567.0), 15f)
    googleMap.moveCamera(cameraPosition)
}
