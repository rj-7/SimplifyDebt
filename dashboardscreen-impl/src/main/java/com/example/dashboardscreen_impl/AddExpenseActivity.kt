package com.example.dashboardscreen_impl

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

internal class AddExpenseActivity : AppCompatActivity() {

    companion object {
        const val GROUP_ITEM = "GROUP_ITEM"
        fun getIntent(context: Context, groupItem: GroupItem): Intent =
            Intent(context, AddExpenseActivity::class.java).apply {
                putExtra(GROUP_ITEM, groupItem)
            }
    }

    private val viewModel: AddExpenseViewModel by viewModels<AddExpenseViewModel> {
        AddExpenseViewModel.provideFactory(FirebaseAuth.getInstance())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addexpense)
        val group = intent.getParcelableExtra<GroupItem>(GROUP_ITEM)
        addFragment(AddExpenseFragment.getInstance(group))
        viewModel.navigationLiveData.observe(this) {
            when (it) {
                is AddExpenseViewModel.AddExpenseNavigationEvent.GoToAddExpense -> {
                    addFragment(AddExpenseFragment.getInstance(group))
                }
                is AddExpenseViewModel.AddExpenseNavigationEvent.GoToSelectUsers -> {
                    addFragment(SplitFriendsFragment.getInstance(it.amount))
                }
                is AddExpenseViewModel.AddExpenseNavigationEvent.GoBack -> {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }
        }
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

    override fun onBackPressed() {
        if (supportFragmentManager.fragments.size == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

}
