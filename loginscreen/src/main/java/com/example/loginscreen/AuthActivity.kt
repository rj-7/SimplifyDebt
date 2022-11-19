package com.example.loginscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class AuthActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context) = Intent(context, AuthActivity::class.java).apply {

        }
    }

    val viewModel: AuthViewModel by viewModels<AuthViewModel> {
        AuthViewModel.provideFactory(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        viewModel.navigationLiveData.observe(this) {
            when (it) {
                AuthViewModel.NavigationEvent.GoToLogin -> {
                    addFragment(LoginFragment.newInstance())
                }
                AuthViewModel.NavigationEvent.GoToRegister -> {
                    addFragment(RegisterFragment.newInstance(), true)
                }
            }
        }
        viewModel.navigationLiveData.value = AuthViewModel.NavigationEvent.GoToLogin
    }

    private fun addFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        val ft = supportFragmentManager.beginTransaction().setReorderingAllowed(true)
        if (supportFragmentManager.fragments.isNullOrEmpty()) {
            ft.add(R.id.auth_container, fragment)
        } else {
            ft.replace(R.id.auth_container, fragment)
        }
        ft.addToBackStack(null)
        ft.commit()
    }
}