package com.example.simplifydebt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dashboardscreen.DashboardActivity
import com.example.loginscreen.AuthActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        auth = FirebaseAuth.getInstance()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if(currentUser != null){
//            startActivity(DashboardActivity.getIntent(this))
//        }
//        else
//        {
            startActivity(AuthActivity.getIntent(this))
 //       }

    }
}