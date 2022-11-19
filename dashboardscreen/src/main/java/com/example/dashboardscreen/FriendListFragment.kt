package com.example.dashboardscreen

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dashboardscreen.databinding.FragmentFriendListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

internal class FriendListFragment : Fragment() {

    companion object {
        fun newInstance() = FriendListFragment().apply {
            Bundle().apply {

            }
        }
    }

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentFriendListBinding? = null
    private val binding: FragmentFriendListBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFriendListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.friendListRV.layoutManager = LinearLayoutManager(requireContext())
//        auth = FirebaseAuth.getInstance()
//        var userUid = auth.currentUser?.uid;
//        val db = Firebase.firestore
//
//        var doc: Map<String, Any>? = null
//        val docRef = userUid?.let { db.collection("Users").document(it) }
//        docRef?.get()?.addOnSuccessListener { document ->
//            doc= document.data as Map<String, Any>
//        }?.addOnFailureListener { exception ->
//            Log.d(ContentValues.TAG, "get failed with ", exception)
//        }
//
//        binding.userNameTextView.text = doc?.get("firstName") as CharSequence?

        val adapter = FriendListAdapter(requireContext()) {

        }
        adapter.setUserList(
            listOf(
                UserItem(
                    userId = 123,
                    firstName = "Rakshith",
                    lastName = "Test",
                    phoneNo = "",
                    email = ""
                )
            )
        )
        binding.friendListRV.adapter = adapter
    }
}