package com.example.dashboard_api

import android.content.Context
import android.content.Intent
import com.example.navigator.Feature

interface DashboardFeature: com.example.navigator.Feature {
    fun getDashBoardIntent(context: Context): Intent
}