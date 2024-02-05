package com.example.firebasep
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import android.content.Intent

class ParkingFragment : Fragment() {

    private lateinit var btnUnityHallParking: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_myparking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnUnityHallParking = view.findViewById(R.id.btnUnityHallParking)
        btnUnityHallParking.setOnClickListener {
            navigateToAnotherActivity()
        }
    }

    private fun navigateToAnotherActivity() {
        val intent = Intent(activity, DisplayParkingActivity::class.java)
        startActivity(intent)
    }
}
