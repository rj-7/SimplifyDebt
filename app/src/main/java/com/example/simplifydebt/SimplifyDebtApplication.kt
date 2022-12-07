package com.example.simplifydebt

import android.app.Application
import com.example.dashboard_api.DashboardFeature
import com.example.dashboardscreen_impl.DashboardModule
import com.example.loginscreen.AuthModule
import com.example.loginscreen_api.AuthFeature
import com.example.navigator.Navigator

class SimplifyDebtApplication : Application(), Navigator {

    override fun onCreate() {
        super.onCreate()
    }

    override fun <T> getFeature(featureClass: Class<T>): T {
        return when (featureClass) {
            DashboardFeature::class.java -> DashboardModule.getDashBoardFeature() as T
            AuthFeature::class.java -> AuthModule.getAuthFeature() as T
            else -> DashboardModule.getDashBoardFeature() as T
        }
    }
}