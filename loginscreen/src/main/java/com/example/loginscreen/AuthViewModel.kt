package com.example.loginscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

class AuthViewModel(loginState: Boolean) : ViewModel() {

    companion object {
        fun provideFactory(loginState: Boolean) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                return AuthViewModel(loginState) as T
            }
        }
    }

    val navigationLiveData: MutableLiveData<NavigationEvent> = MutableLiveData()

    sealed class NavigationEvent {
        object GoToRegister : NavigationEvent()
        object GoToLogin : NavigationEvent()
    }
}