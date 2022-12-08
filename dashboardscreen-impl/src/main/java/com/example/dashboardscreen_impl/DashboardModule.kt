package com.example.dashboardscreen_impl

//PATTERN: SINGLETON
// feature module initialised once when the app starts to provide feature implementations

object DashboardModule {
    fun getDashBoardFeature(): com.example.dashboard_api.DashboardFeature {
        return DashboardFeatureImpl()
    }
}