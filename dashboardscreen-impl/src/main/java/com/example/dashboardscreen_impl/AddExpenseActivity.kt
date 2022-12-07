package com.example.dashboardscreen_impl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore

class AddExpenseActivity: AppCompatActivity() {
    lateinit var db: FirebaseFirestore

    companion object {
        fun getIntent(context: Context): Intent =
            Intent(context, AddExpenseActivity::class.java).apply {

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
