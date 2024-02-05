package com.example.firebasep.Geofence

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import android.Manifest
import android.util.Log
import com.google.firebase.database.*



class GeofenceBackgroundService : Service() {

    private lateinit var geofenceHelper: GeofenceHelper
    private lateinit var geofencingClient: GeofencingClient

    override fun onCreate() {
        super.onCreate()
        geofenceHelper = GeofenceHelper(this)
        geofencingClient = LocationServices.getGeofencingClient(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Define your geofence parameters
        val geofenceID = "WPI"
        val geofenceRadius = 700 // Set your geofence radius
//        val geofenceLatLng = LatLng(42.282129, -71.754384)
        val geofenceLatLng  = LatLng(42.27384685833052, -71.80971805810996)
        // Create the geofence
        val geofence = geofenceHelper.getGeofence(
            geofenceID,
            geofenceLatLng,
            geofenceRadius,
            Geofence.GEOFENCE_TRANSITION_ENTER
        )

        // Create the geofencing request
        val geofencingRequest = geofenceHelper.getGeofencingRequest(geofence)

        // Create the pending intent
        val pendingIntent = geofenceHelper.CreatePendingIntent()

        // Check and request location permission if needed
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Handle permission request logic here
            return START_NOT_STICKY
        }

        // Add geofences using geofencingClient
        geofencingClient.addGeofences(geofencingRequest, pendingIntent)?.run {
            addOnSuccessListener {
                Log.d(TAG, "Geofence added successfully")
            }
            addOnFailureListener {
                val errorMessage = geofenceHelper.getErrorString(it)
                Log.e(TAG, "Failed to add geofence: $errorMessage")
            }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        private const val TAG = "GeofenceBackgroundSvc"
    }
}
