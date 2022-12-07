package com.example.dashboardscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dashboardscreen.databinding.FragmentFriendDetailsBinding
import com.example.dashboardscreen.databinding.FragmentGroupDetailsBinding
import com.google.firebase.auth.FirebaseAuth

class GroupDetailsFragment: Fragment() {
    companion object {
        private const val GROUP_ID = "GROUP_ID"
        fun newInstance(groupId: String) = GroupListFragment().apply {
            arguments = Bundle().apply {
                putString(GROUP_ID, groupId)
            }
        }
    }

    private var _binding: FragmentGroupDetailsBinding? = null
    private val binding: FragmentGroupDetailsBinding
        get() = _binding!!
    private val viewModel: DashboardViewModel by activityViewModels<DashboardViewModel> {
        DashboardViewModel.provideFactory(FirebaseAuth.getInstance())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGroupDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val friendId = arguments?.getString(GROUP_ID) ?: ""
        val adapter = ExpenseListAdapter(requireContext()) {

        }
        binding.expensesRv.adapter = adapter
        binding.expensesRv.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getUserData(friendId).observe(viewLifecycleOwner) {
            if (it != null) {
                binding.friendName.text = it.firstName + ' ' + it.lastName
                binding.initialsTextView.text =
                    "${it.firstName?.get(0)}${it.lastName?.get(0)}".toUpperCase()
            }
        }

        viewModel.getUserExpenses(viewModel.userData?.id).observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                val expensesList = viewModel.getFriendExpenses(it, friendId)
                adapter.setExpenseList(expensesList)
            }
        }
    }
}