package com.example.mapsandlocation

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onStart(){
        requestFineLocation()
        super.onStart()
        when{
            isLocationGranted() ->{
                setUpLocationListener()
            }else->{
            requestFineLocation()
        }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            777-> if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                setUpLocationListener()
            }else{
                Toast.makeText(this, "Permission not granted", LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpLocationListener() {

    }

    fun isLocationGranted(): Boolean {
        return checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestFineLocation() {
        this.requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            777
        )
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isZoomGesturesEnabled = true
            isMyLocationButtonEnabled = true
            isCompassEnabled = true
        }
        mMap.setMaxZoomPreference(14f)

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.addPolyline(
            PolylineOptions()
                .add(sydney, LatLng(20.59,78.39))
                .color(ContextCompat.getColor(baseContext, R.color.colorPrimary))
        )
            .width = 2f

    }
}