//Mar
package com.example.mapview


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.google.android.gms.location.GeofenceStatusCodes
import kotlin.properties.Delegates
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import androidx.core.app.NotificationCompat

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    private val TAG = "GeofenceBroadcastReceive"
    private lateinit var mainActivity: MainActivity

    override fun onReceive(context: Context?, intent: Intent?) {

        //NE
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Create a notification channel for API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("GeofenceChannel", "Geofence Notifications", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }//NE


        val geofencingEvent = intent?.let { GeofencingEvent.fromIntent(it) }

        if (geofencingEvent != null) {
            if (geofencingEvent.hasError()) {
                val errorMessage = GeofenceStatusCodes.getStatusCodeString(geofencingEvent.errorCode)
                Toast.makeText(context?.applicationContext, "Error in broadcast receiver", Toast.LENGTH_SHORT).show()
                return
            }
        }
        val geofenceList: MutableList<Geofence>? = geofencingEvent?.triggeringGeofences
        if (geofenceList != null) {
            for (geofence in geofenceList) {
                Log.d(TAG, "onReceive: ${geofence.requestId}")
            }
        }

        val geofenceTransition = geofencingEvent?.geofenceTransition

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL && geofenceList != null) {
            for (geofence in geofenceList) {
                val notificationIntent = Intent(context, MainActivity::class.java)
                val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

                val notification = NotificationCompat.Builder(context, "GeofenceChannel")
                    .setSmallIcon(R.drawable.ic_launcher_background) // replace with your app's icon
                    .setContentTitle("Geofence Alert")
                    .setContentText("You're inside the ${geofence.requestId} geofence")
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH) // Consider using PRIORITY_MAX if you want even higher visibility
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // This line sets the notification visibility
                    .setAutoCancel(true)
                    .build()


                notificationManager.notify(geofence.requestId.hashCode(), notification)
            }
        }

    }
}



