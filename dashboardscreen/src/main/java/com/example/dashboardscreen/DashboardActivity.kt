package com.example.dashboardscreen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

internal class DashboardActivity: Activity() {

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, DashboardActivity::class.java).apply {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}