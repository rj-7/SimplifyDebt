package com.example.dashboardscreen_impl

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

internal class DashboardPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FriendListFragment.newInstance()
            1 -> GroupListFragment.newInstance()
            else -> FriendListFragment.newInstance()
        }
    }
}