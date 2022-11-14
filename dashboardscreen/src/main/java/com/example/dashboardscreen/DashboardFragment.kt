package com.example.dashboardscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dashboardscreen.databinding.FragmentDashboardBinding
import com.google.android.material.tabs.TabLayoutMediator

internal class DashboardFragment : Fragment() {
    companion object {
        fun getInstance() = DashboardFragment().apply {
            Bundle().apply {

            }
        }
    }

    private var _binding: FragmentDashboardBinding? = null
    private val binding: FragmentDashboardBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TabLayoutMediator(binding.tabLayout, binding.dashboardPager) { tab, position ->
            tab.text = when (position) {
                0 -> "FRIENDS"
                1 -> "GROUPS"
                else -> "ACTIVITIES"
            }
        }
    }
}