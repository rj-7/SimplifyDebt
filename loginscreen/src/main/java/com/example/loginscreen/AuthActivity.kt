package com.example.loginscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class AuthActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context) = Intent(context, AuthActivity::class.java).apply {

        }
    }

    val viewModel: AuthViewModel by viewModels<AuthViewModel> {
        AuthViewModel.provideFactory(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }
}