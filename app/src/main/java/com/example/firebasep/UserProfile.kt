package com.example.firebasep

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import android.widget.EditText
import android.util.Log
import android.content.Intent
import com.example.firebasep.MainActivity
import android.widget.Button
import android.widget.TextView

class UserProfileFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference


    private lateinit var editTextName: TextView
    private lateinit var editTextEmail: TextView
    private lateinit var editTextPhone: TextView
    private lateinit var editTextVehicleName: TextView
    private lateinit var editTextVehicleNumber: TextView
    private lateinit var editTextVehicleModel: TextView
    private lateinit var editTextVehicleColor: TextView
    private lateinit var editTextVehicleYear: TextView
    private lateinit var logoutButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.user_profile, container, false)

        // Initialize EditText elements
        editTextName = view.findViewById(R.id.editTextName)
        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextPhone = view.findViewById(R.id.editTextPhone)
        editTextVehicleName = view.findViewById(R.id.editTextVehicleName)
        editTextVehicleNumber = view.findViewById(R.id.editTextVehicleNumber)
        editTextVehicleModel = view.findViewById(R.id.editTextVehicleModel)
        editTextVehicleColor = view.findViewById(R.id.editTextVehicleColor)
        editTextVehicleYear = view.findViewById(R.id.editTextVehicleYear)
        logoutButton = view.findViewById(R.id.button2)

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        val userEmail = arguments?.getString("USER_EMAIL")
        userEmail?.let { fetchUserData(it) }

        logoutButton.setOnClickListener {
            activity?.let { context ->
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                context.finish() // Finish the current activity
            }
        }

        return view
    }

    private fun fetchUserData(userEmail: String) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var isUsernameFound = false

                for (userSnapshot in dataSnapshot.children) {
                    val uEmail = userSnapshot.child("uemail").getValue(String::class.java)
                    if (uEmail != null && uEmail.equals(userEmail, ignoreCase = true)) {
                        // Set data to EditText fields
                        editTextName.setText(userSnapshot.child("uname").getValue(String::class.java))
                        editTextEmail.setText(uEmail) // Assuming uEmail is the email to display
                        editTextPhone.setText(userSnapshot.child("phone").getValue(String::class.java))
                        editTextVehicleName.setText(userSnapshot.child("carModel").getValue(String::class.java)) // Assuming 'carModel' is equivalent to 'Vehicle Name'
                        editTextVehicleNumber.setText(userSnapshot.child("carPlate").getValue(String::class.java))
                        editTextVehicleModel.setText(userSnapshot.child("carModel").getValue(String::class.java))
                        editTextVehicleColor.setText(userSnapshot.child("carColor").getValue(String::class.java))
                        editTextVehicleYear.setText(userSnapshot.child("carYear").getValue(String::class.java))

                        isUsernameFound = true
                        break
                    }
                }

                if (!isUsernameFound) {
                    Log.d("UserProfileFragment", "User with the email $userEmail not found.")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("UserProfileFragment", "Error: ${databaseError.message}")
            }
        })
    }
}
