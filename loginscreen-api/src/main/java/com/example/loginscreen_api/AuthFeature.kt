package com.example.loginscreen_api

import android.content.Context
import android.content.Intent
import com.example.navigator.Feature

interface AuthFeature : com.example.navigator.Feature {
    fun getLoginScreenIntent(context: Context): Intent
}