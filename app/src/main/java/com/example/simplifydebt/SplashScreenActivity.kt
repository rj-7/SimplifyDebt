package com.example.simplifydebt

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

internal class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}