package com.example.dashboardscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dashboardscreen.databinding.FragmentFriendListBinding

internal class FriendListFragment : Fragment() {

    companion object {
        fun newInstance() = FriendListFragment().apply {
            Bundle().apply {

            }
        }
    }

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