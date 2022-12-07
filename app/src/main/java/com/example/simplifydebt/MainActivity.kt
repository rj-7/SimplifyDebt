package com.example.simplifydebt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dashboard_api.DashboardFeature
import com.example.loginscreen_api.AuthFeature
import com.example.navigator.Navigator
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigator = application as Navigator
        FirebaseAuth.getInstance().addAuthStateListener {
            val intent = if (it.currentUser != null) {
                navigator.getFeature(DashboardFeature::class.java).getDashBoardIntent(this)
            } else {
                navigator.getFeature(AuthFeature::class.java).getLoginScreenIntent(this)
            }
            startActivity(intent)
        }
    }
}