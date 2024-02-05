//Mar
package com.example.firebasep.Geofence


import android.app.Notification
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
import android.graphics.Color
import android.os.Build
import android.widget.Button
import android.widget.GridLayout
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.firebasep.DataModels.ParkingSpotsModel
import com.example.firebasep.MainActivity
import com.example.firebasep.NavigationView
import com.example.firebasep.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    private val TAG = "GeofenceBroadcastReceive"

    override fun onReceive(context: Context?, intent: Intent?) {
        val channelId = "i.apps.notifications"
        val description = "Test notification"

        val geofencingEvent = intent?.let { GeofencingEvent.fromIntent(it) }

        if (geofencingEvent != null && geofencingEvent.hasError()) {
            val errorMessage = GeofenceStatusCodes.getStatusCodeString(geofencingEvent.errorCode)
            Toast.makeText(context?.applicationContext, "Error in broadcast receiver: $errorMessage", Toast.LENGTH_SHORT).show()
            return
        }

        val geofenceList: List<Geofence> = geofencingEvent?.triggeringGeofences ?: return

        if (geofencingEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            Toast.makeText(context?.applicationContext, "Enter Geofence", Toast.LENGTH_SHORT).show()

            val numOfParking = fetchDataFromFirebase()

            val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val notificationIntent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val notification = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Geofence Alert")
                .setContentText("You're around the WPI and ${numOfParking} parkings are available in UnityHall parking")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOngoing(true) // Set the notification as ongoing
                .build()

            // Create a notification channel for API 26+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)
            }

            notificationManager.notify(1234, notification)
        }
    }

    private fun fetchDataFromFirebase(): Int {
        Log.e("INsidee Fetch data", "Inside FtechData")
        val databaseReference = FirebaseDatabase.getInstance().getReference("parkingSlotUnity")
        var numOfParking: Int = 0

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Log.e("FirebaseConnection", "No data found in the database.")
                    return
                }

                for (ParkingSnapshot in dataSnapshot.children) {
                    if(ParkingSnapshot.key == "Total") {
                        val numberValue = (ParkingSnapshot.value as? Map<*, *>)?.get("number") as? Long
                        Log.d("FirebaseConnection", "total: $numberValue ")
                        Log.d("MainActivity", "FRom MainActy sending User_Email: $numberValue")

                        numOfParking = numberValue?.toInt() ?: 0

                    }

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("geofence", "Database error", databaseError.toException())
            }
        })

        return numOfParking
    }


}



