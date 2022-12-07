package com.example.dashboardscreen_impl

import android.content.Context
import android.content.Intent

internal class DashboardFeatureImpl: com.example.dashboard_api.DashboardFeature {
    override fun getDashBoardIntent(context: Context): Intent {
        return DashboardActivity.getIntent(context)
    }
}