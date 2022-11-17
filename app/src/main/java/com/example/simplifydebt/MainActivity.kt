package com.example.simplifydebt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dashboardscreen.DashboardActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(DashboardActivity.getIntent(this))
    }
}