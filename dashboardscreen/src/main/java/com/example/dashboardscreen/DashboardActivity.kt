package com.example.dashboardscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess

class DashboardActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context): Intent =
            Intent(context, DashboardActivity::class.java).apply {

            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(findViewById(R.id.toolbar))
        val fragment = DashboardFragment.getInstance()
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .add(R.id.container, fragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.login -> {

            }
            R.id.exit -> {
                finishAffinity()
                exitProcess(0)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}