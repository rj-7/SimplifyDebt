package com.example.dashboardscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dashboardscreen.databinding.FragmentFriendListBinding
import com.google.firebase.auth.FirebaseAuth

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
    private val viewModel: DashboardViewModel by activityViewModels<DashboardViewModel> {
        DashboardViewModel.provideFactory(FirebaseAuth.getInstance())
    }

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
            viewModel.navigationLiveData.value =
                DashboardViewModel.DashboardNavigationEvent.GoToFriendDetails(it.id)
        }
        binding.friendListRV.adapter =  adapter
        val friendIds = viewModel.userData?.friends
        viewModel.getFriendsData(friendIds).observe(viewLifecycleOwner) { friendUsers ->
            if (friendUsers != null) {
                viewModel.getUserExpenses(viewModel.userData?.id)
                    .observe(viewLifecycleOwner) { userExpenses ->
                        val friendExpenses = viewModel.getFriendExpenses(userExpenses, friendUsers)
                        adapter.setUserList(friendUsers)
                    }
            }
        }
    }
}