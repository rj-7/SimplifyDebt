package com.example.loginscreen

//PATTERN: SINGLETON
// feature module initialised once when the app starts to provide feature implementations
object AuthModule {
    fun getAuthFeature(): com.example.loginscreen_api.AuthFeature {
        return AuthFeatureImpl()
    }
}