package com.example.firebasep

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.firebasep.Geofence.GeofenceBackgroundService
import com.example.firebasep.Geofence.GeofenceHelper
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {

    private lateinit var textEmail: EditText
    private lateinit var textPassword: EditText
    private lateinit var btnFetchData: Button
    private lateinit var btnInserData: Button
    //private lateinit var btnManagementUser: Button


    //geofence
//    private val TAG = "MainActivity"
    private lateinit var mMap: GoogleMap
    private lateinit var geofencingClient: GeofencingClient
//    private lateinit var locationManager: LocationManager
    private val locationCode = 2000
//    private val GEOFENCE_RADIUS = 500
    private lateinit var geofenceHelper: GeofenceHelper

//    private val GEOFENCE_ID = "WPI"
    //NE
    // Define your destination locations globally if they are fixed and reused
//    private val around_wpi = LatLng(42.27384685833052, -71.80971805810996)
//    private val around_wpi = LatLng(42.282129, -71.754384)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textEmail = findViewById(R.id.textEmail)
        textPassword = findViewById(R.id.textPassword)
        btnFetchData = findViewById(R.id.btnFetchData)
        btnInserData = findViewById(R.id.btnInsertData)
       // btnManagementUser = findViewById(R.id.btnManagementUsers)


        btnFetchData.setOnClickListener {
            val userEmail = textEmail.text.toString().trim()
            Log.d("MainActivity22", "FRom MainActy sending User_Email: $userEmail")
            if (validateUsername() && validatePassword()) {

              //  checkUser(object : LoginSuccessCallback {

                 //   override fun onLoginSuccess() {
                        Log.d("MainActivity33", "FRom MainActy sending User_Email: $userEmail")
                        // This is called if login is successful
                        Toast.makeText(applicationContext, "Login successful!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@MainActivity , ParkingActivity::class.java)
                        val userEmail = textEmail.text.toString().trim()
                        Log.d("MainActivity", "FRom MainActy sending User_Email: $userEmail")
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("USER_EMAIL", userEmail)

                        startActivity(intent)

                  //  }

                  //  override fun onLoginFailure(message: String) {
                        // This is called if login fails
                  //      Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                  //  }
               // })
            }
        }

        btnInserData.setOnClickListener {

            val intent = Intent(this@MainActivity, CreateActivity::class.java)
            startActivity(intent)
        }

//        btnManagementUser.setOnClickListener {
//            val intent = Intent(this@MainActivity, DisplayUsersActivity::class.java)
//            startActivity(intent)
//        }

        geofencingClient = LocationServices.getGeofencingClient(this)
        geofenceHelper = GeofenceHelper(this)

//        addGeofence(around_wpi, GEOFENCE_RADIUS, GEOFENCE_ID)
        startGeofenceService()


    }

    private fun validateUsername(): Boolean {
        val valUsername = textEmail.text.toString()
        if (valUsername.isEmpty()) {
            textEmail.error = "Email cannot be empty!"
            return false
        } else if (!valUsername.endsWith("@wpi.edu")) {
            textEmail.error = "Login with your WPI credential!"
            return false
        } else {
            textEmail.error = null
            return true
        }
    }

    private fun validatePassword(): Boolean {
        val valPassword = textPassword.text.toString()
        if (valPassword.isEmpty()) {
            textPassword.error = "Password cannot be empty!"
            return false
        } else if (valPassword.length < 6) {
            textPassword.error = "Password must be at least 6 characters!"
            return false
        } else {
            textPassword.error = null
            return true
        }
    }
    interface LoginSuccessCallback {
        fun onLoginSuccess()
        fun onLoginFailure(message: String)
    }
    private fun checkUser(callback: LoginSuccessCallback) {
        val userUsername = textEmail.text.toString().trim()
        val userPassword = textPassword.text.toString().trim()


        val reference = FirebaseDatabase.getInstance().getReference("Users")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var isUsernameFound = false
                var storedPassword: String? = null
                var userType: String? = null


                for (driverSnapshot in dataSnapshot.children) {
                    val uEmail = driverSnapshot.child("uemail").getValue(String::class.java)
                    if (uEmail != null && uEmail.toLowerCase() == userUsername.toLowerCase()) {
                        storedPassword = driverSnapshot.child("upass").getValue(String::class.java)
                        userType = driverSnapshot.child("uname").getValue(String::class.java)
                        Log.d("UserTypeCheck", "User type: $userType")
                        isUsernameFound = true
                        break
                    }
                }

                if (isUsernameFound) {
                    if (storedPassword != null && storedPassword == userPassword) {
                        if (userType.equals("Admin", ignoreCase = true)) {
                            val adminIntent = Intent(this@MainActivity, DisplayUsersActivity::class.java)
                            startActivity(adminIntent)
                        } else {
                            val userIntent = Intent(this@MainActivity, ParkingActivity::class.java)
                            startActivity(userIntent)
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Password does not match.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Username(Email) not found", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
                Log.w("DatabaseError", "Failed to read value.", databaseError.toException())
            }
        })
    }


    //Geofence
//    private fun addGeofence(latLng: LatLng, radius: Int, ID: String) {
//        val geofence = geofenceHelper.getGeofence(ID, latLng, radius, Geofence.GEOFENCE_TRANSITION_DWELL or Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
//        val geofencingRequest = geofenceHelper.getGeofencingRequest(geofence)
//        val pendingIntent = geofenceHelper.CreatePendingIntent()
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        }
//        geofencingClient.addGeofences(geofencingRequest, pendingIntent)
//            .addOnSuccessListener {
//                Log.d(TAG, "onSuccess: Geofence Added...")
//            }
//            .addOnFailureListener { e ->
//                val errorMessage = geofenceHelper.getErrorString(e)
//                Log.d(TAG, "onFailure: $errorMessage")
//            }
//    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if ((ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED) &&
                    (ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED)
                ) {
                    return
                }
                mMap.isMyLocationEnabled = true
            }
        }
    }

    private fun startGeofenceService() {
        val serviceIntent = Intent(this, GeofenceBackgroundService::class.java)
        startService(serviceIntent)
    }

}
