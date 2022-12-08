package com.example.dashboardscreen_impl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dashboardscreen_impl.databinding.FragmentFriendDetailsBinding
import com.google.firebase.auth.FirebaseAuth

internal class FriendDetailsFragment : Fragment() {
    companion object {
        private const val FRIEND_USER_ID = "FRIEND_USER_ID"
        fun newInstance(friendId: String) = FriendDetailsFragment().apply {
            arguments = Bundle().apply {
                putString(FRIEND_USER_ID, friendId)
            }
        }
    }

    private var _binding: FragmentFriendDetailsBinding? = null
    private val binding: FragmentFriendDetailsBinding
        get() = _binding!!
    private val viewModel: DashboardViewModel by activityViewModels<DashboardViewModel> {
        DashboardViewModel.provideFactory(FirebaseAuth.getInstance())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val friendId = arguments?.getString(FRIEND_USER_ID) ?: ""
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
        binding.amountText.text = "$50"


        viewModel.getUserExpenses(FirebaseAuth.getInstance().currentUser?.uid)
            .observe(viewLifecycleOwner) {
                if (!it.isNullOrEmpty()) {
                    val userBalance = viewModel.BalanceWithFriends(
                        it,
                        FirebaseAuth.getInstance().currentUser?.uid,
                        friendId
                    )
                    binding.amountText.text = "$" + userBalance.totalBalance.toString()
                    val expensesList = viewModel.getFriendExpenses(it, friendId)
                    adapter.setExpenseList(expensesList)
                }
            }

        binding.buttonSettleUp.setOnClickListener {
            viewModel.navigationLiveData.value =
                DashboardViewModel.DashboardNavigationEvent.GoToSettle(
                    yourName = viewModel.userData?.firstName + " " + viewModel.userData?.lastName,
                    friendName = binding.friendName.text.toString(),
                    amount = binding.amountText.text.toString()
                )
        }
    }
}