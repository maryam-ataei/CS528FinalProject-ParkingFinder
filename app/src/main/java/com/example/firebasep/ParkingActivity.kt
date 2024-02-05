package com.example.firebasep

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.firebasep.databinding.ActivityParkingBinding
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext

class ParkingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParkingBinding
    private var userEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityParkingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userEmail = intent.getStringExtra("USER_EMAIL")
        Log.d("ParkingActivity", "Call from PA Received User Email: $userEmail")


        replaceFragment(ParkingFragment())


        binding.bottomNavigationView.apply {
            setBackground(null)
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.subscriptions -> replaceFragmentWithUserEmail()
                    R.id.home -> startMyActivity(this.context)
                    R.id.myparking -> replaceFragment(ParkingFragment())
                    R.id.more -> replaceFragment(MoreFragment())
                }
                true
            }
        }
    }

    private fun startMyActivity(context: Context) {
        val intent = Intent(context, HistoryGrapgh::class.java)
        context.startActivity(intent)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

    private fun replaceFragmentWithUserEmail() {
        val userProfileFragment = UserProfileFragment().apply {
            arguments = Bundle().apply {
                putString("USER_EMAIL", userEmail)
            }
        }
        replaceFragment(userProfileFragment)
    }
}