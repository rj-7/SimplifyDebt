package com.example.simplifydebt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dashboardscreen.DashboardActivity
import com.example.loginscreen.AuthActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseAuth.getInstance().addAuthStateListener {
            val intent = if (it.currentUser != null) {
                DashboardActivity.getIntent(this)
            } else {
                AuthActivity.getIntent(this)
            }
            startActivity(intent)
        }
    }
}