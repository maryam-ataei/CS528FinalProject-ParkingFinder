package com.example.firebasep


import java.util.Timer
import java.util.TimerTask
import com.example.firebasep.DataModels.*



fun main() {
    val timer = Timer()
    val task = object : TimerTask() {
        override fun run() {
            val host = "127.0.0.1"
            val port = 65432



            try {
                val jsonString = receiveJsonStringFromSocket(host, port)
                val parkingSpots = parseJson(jsonString)

                if (validateData(parkingSpots)) {
                    updateFirebaseDatabase(parkingSpots)
                    println("Database updated successfully.")
                } else {
                    println("Received data is invalid.")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // run every 5 minutes (300000 milliseconds)
    timer.schedule(task, 0L, 300000L)
}

