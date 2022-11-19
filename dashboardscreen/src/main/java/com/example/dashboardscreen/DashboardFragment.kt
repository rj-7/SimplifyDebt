package com.example.dashboardscreen

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dashboardscreen.databinding.FragmentDashboardBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

internal class DashboardFragment : Fragment() {
    companion object {
        fun getInstance() = DashboardFragment().apply {
            Bundle().apply {

            }
        }
    }
    private lateinit var auth: FirebaseAuth

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
        binding.dashboardPager.adapter = DashboardPagerAdapter(requireActivity())
        auth = FirebaseAuth.getInstance()
        var userUid = auth.currentUser?.uid;
        val db = Firebase.firestore

        var doc: Map<String, Any>? = null
        val docRef = userUid?.let { db.collection("Users").document(it) }
        docRef?.get()?.addOnSuccessListener { document ->
            doc= document.data as Map<String, Any>
        }?.addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        binding.userNameTextView.text = doc?.get("firstName") as CharSequence?

        TabLayoutMediator(binding.tabLayout, binding.dashboardPager) { tab, position ->
            tab.text = when (position) {
                0 -> "FRIENDS"
                1 -> "GROUPS"
                else -> "ACTIVITIES"
            }
        }.attach()
    }
}