package com.example.communityhealth.views.editlocation

import android.app.Activity
import android.content.Intent
import com.example.communityhealth.models.Location
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import android.content.Context
import android.location.Address
import android.location.Geocoder
import java.io.IOException
import java.util.*

class EditLocationPresenter(private val view: EditLocationView) {

    var location: Location = view.intent.extras?.getParcelable("location") ?: Location()

    fun initMap(map: GoogleMap) {
        // Set the map type to default (normal) view
        map.mapType = GoogleMap.MAP_TYPE_NORMAL

        val loc = LatLng(location.lat, location.lng)
        getAddressDetails(view, loc) { roadName, town, eircode ->
            val snippet = if (town == "Unknown Town" && eircode == "No EIRCODE") {
                "GPS: ${loc.latitude}, ${loc.longitude}"
            } else {
                "$roadName, $town, $eircode"
            }

            val options = MarkerOptions()
                .title("Patient Location")
                .snippet(snippet)
                .draggable(true)
                .position(loc)
            map.addMarker(options)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
            map.setOnMarkerDragListener(view)
            map.setOnMarkerClickListener(view)
        }
    }

    private fun getAddressDetails(context: Context, loc: LatLng, callback: (String, String, String) -> Unit) {
        Thread {
            try {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses: List<Address>? = geocoder.getFromLocation(loc.latitude, loc.longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0]
                    val roadName = address.thoroughfare ?: "Unknown Road"
                    val town = address.locality ?: "Unknown Town"
                    val eircode = address.postalCode ?: "No EIRCODE"
                    (context as Activity).runOnUiThread {
                        callback(roadName, town, eircode)
                    }
                } else {

                    callback("Unknown Road", "Unknown Town", "No EIRCODE")
                }
            } catch (e: IOException) {
                e.printStackTrace()

                (context as Activity).runOnUiThread {
                    callback("Unknown Road", "Unknown Town", "No EIRCODE")
                }
            }
        }.start()
    }

    fun doUpdateLocation(lat: Double, lng: Double, zoom: Float) {
        location.lat = lat
        location.lng = lng
        location.zoom = zoom
    }

    fun doOnBackPressed() {
        val resultIntent = Intent()
        resultIntent.putExtra("location", location)
        view.setResult(Activity.RESULT_OK, resultIntent)
        view.finish()
    }

    fun doUpdateMarker(marker: Marker) {
        getAddressDetails(view, LatLng(marker.position.latitude, marker.position.longitude)) { roadName, town, eircode ->
            marker.title = "Patient Address"
            marker.snippet = "$roadName, $town, $eircode"
            marker.showInfoWindow()
        }
    }
}
