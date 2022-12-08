package com.example.dashboardscreen_impl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.system.exitProcess

internal class DashboardActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
// ...
// Initialize Firebase Auth


    companion object {
        fun getIntent(context: Context): Intent =
            Intent(context, DashboardActivity::class.java).apply {

            }
    }

    private val viewModel: DashboardViewModel by viewModels<DashboardViewModel> {
        DashboardViewModel.provideFactory(FirebaseAuth.getInstance())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(findViewById(R.id.toolbar))
        viewModel.getUserData(FirebaseAuth.getInstance().currentUser?.uid).observe(this) {
            viewModel.userData = it
            addFragment(DashboardFragment.getInstance())
        }
        viewModel.navigationLiveData.observe(this) {
            when (it) {
                is DashboardViewModel.DashboardNavigationEvent.GoToFriendDetails -> {
                    addFragment(FriendDetailsFragment.newInstance(it.friendIds ?: ""))
                }
                is DashboardViewModel.DashboardNavigationEvent.GoToGroupDetails -> {
                    addFragment(GroupDetailsFragment.newInstance(it.groupItem))
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                Firebase.auth.signOut()
                finish()
            }
            R.id.exit -> {
                finishAffinity()
                exitProcess(0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        val ft = supportFragmentManager.beginTransaction().setReorderingAllowed(true)
        if (supportFragmentManager.fragments.isNullOrEmpty()) {
            ft.add(R.id.container, fragment)
        } else {
            ft.replace(R.id.container, fragment)
        }
        ft.addToBackStack(null)
        ft.commit()
    }
}