package com.example.firebasep

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.Socket

fun receiveJsonStringFromSocket(host: String, port: Int): String {
    var socket: Socket? = null

    try {
        socket = Socket(host, port)
        BufferedReader(InputStreamReader(socket.getInputStream())).use { reader ->
            return reader.readText()
        }
    } catch (e: IOException) {
        e.printStackTrace()
        // You can return a specific error message or throw a custom exception
        return "Error connecting to server: ${e.message}"
    } finally {
        try {
            socket?.close()
        } catch (e: IOException) {
            // Handle closing exception if needed
            e.printStackTrace()
        }
    }
}
