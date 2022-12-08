package com.example.dashboardscreen_impl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dashboardscreen_impl.databinding.FragmentSplitfriendsBinding
import com.google.firebase.auth.FirebaseAuth

internal class SplitFriendsFragment : Fragment() {

    companion object {
        const val AMOUNT = "AMOUNT"
        fun getInstance(amount: Int) = SplitFriendsFragment().apply {
            Bundle().apply {
                putInt(AMOUNT, amount)
            }
        }
    }

    private var _binding: FragmentSplitfriendsBinding? = null
    private val binding: FragmentSplitfriendsBinding
        get() = _binding!!
    private val viewModel: AddExpenseViewModel by activityViewModels<AddExpenseViewModel> {
        AddExpenseViewModel.provideFactory(FirebaseAuth.getInstance())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplitfriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val debts = ArrayList<Debt>()
        val users = ArrayList<String>()
        var paidByUser: UserServiceItem? = null
        binding.paidUsersRV.layoutManager = LinearLayoutManager(requireContext())
        binding.splitUsersRv.layoutManager = LinearLayoutManager(requireContext())
        val paidAdapter = PaidByFriendAdapter(requireContext())
        val splitToFriendAdapter = SplitToFriendAdapter(requireContext()) { userItem, amount ->
            debts.add(Debt(from = userItem.id, to = null, amount = amount))
        }
        paidAdapter.setUserList(viewModel.groupMembers)
        splitToFriendAdapter.setUserList(viewModel.groupMembers)
        binding.paidUsersRV.adapter = paidAdapter
        binding.splitUsersRv.adapter = splitToFriendAdapter

        binding.confirmSplits.setOnClickListener {
            paidByUser = paidAdapter.getSelectedFriend()
            if (paidByUser != null) {
                users.add(paidByUser?.id ?: "")
                debts.forEach {
                    it.apply { it.to = paidByUser?.id }
                    if (!users.contains(it.from)) {
                        users.add(it.from ?: "")
                    }
                }
                viewModel.expenseItem.apply {
                    this.users = users
                    this.simplifiedDebts = debts
                }
                requireActivity().supportFragmentManager.popBackStackImmediate()
            }
        }
    }
}