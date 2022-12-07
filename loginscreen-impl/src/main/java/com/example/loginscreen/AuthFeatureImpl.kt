package com.example.loginscreen

import android.content.Context
import android.content.Intent

internal class AuthFeatureImpl : com.example.loginscreen_api.AuthFeature {
    override fun getLoginScreenIntent(context: Context): Intent {
        return AuthActivity.getIntent(context)
    }
}