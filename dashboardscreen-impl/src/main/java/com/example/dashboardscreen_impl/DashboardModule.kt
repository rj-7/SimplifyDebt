package com.example.dashboardscreen_impl

object DashboardModule {
    fun getDashBoardFeature(): com.example.dashboard_api.DashboardFeature {
        return DashboardFeatureImpl()
    }
}