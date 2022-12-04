package com.example.dashboardscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlin.system.exitProcess

class AddExpenseActivity: AppCompatActivity() {
    lateinit var db: FirebaseFirestore

    companion object {
        fun getIntent(context: Context): Intent =
            Intent(context, DashboardActivity::class.java).apply {

            }
    }

//    private val viewModel: DashboardViewModel by viewModels<DashboardViewModel> {
//        DashboardViewModel.provideFactory(FirebaseAuth.getInstance())
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addexpense)
        //setSupportActionBar(findViewById(R.id.toolbar))
      //  viewModel.getUserData(FirebaseAuth.getInstance().currentUser?.uid).observe(this) {
       //     viewModel.userData = it
            addFragment(AddExpenseFragment.getInstance())
      //  }
//        viewModel.navigationLiveData.observe(this) {
//            when (it) {
//                is DashboardViewModel.DashboardNavigationEvent.GoToFriendDetails -> {
//                    addFragment(FriendDetailsFragment.newInstance(it.friendIds ?: ""))
//                }
//            }
//        }
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
