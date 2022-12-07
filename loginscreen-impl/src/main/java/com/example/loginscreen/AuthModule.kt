package com.example.loginscreen

object AuthModule {
    fun getAuthFeature(): com.example.loginscreen_api.AuthFeature {
        return AuthFeatureImpl()
    }
}