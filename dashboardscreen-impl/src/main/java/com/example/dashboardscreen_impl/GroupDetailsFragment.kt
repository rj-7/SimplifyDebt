package com.example.dashboardscreen_impl

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dashboardscreen_impl.databinding.FragmentGroupDetailsBinding
import com.google.firebase.auth.FirebaseAuth

internal class GroupDetailsFragment : Fragment() {
    companion object {
        private const val GROUP_USER_ID = "GROUP_USER_ID"
        fun newInstance(groupItem: GroupItem) = GroupDetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(GROUP_USER_ID, groupItem)
                //(GROUP_USER_ID, groupItem)
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
        val groupId = arguments?.getParcelable(GROUP_USER_ID) as GroupItem?
        val adapter = ExpenseListAdapter(requireContext()) {

        }
        binding.expensesRv.adapter = adapter
        binding.expensesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.groupName.text = groupId?.groupDescription
        binding.initialsTextView.text =
            groupId?.groupName
        binding.amountText.text = "$50"
        viewModel.getGroupExpenses(groupId?.groupId).observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                val userBalance=viewModel.totalBalance(it,FirebaseAuth.getInstance().currentUser?.uid)
                binding.amountText.text = "$" + userBalance.totalBalance.toString()
            }
        }

        binding.buttonAddExpense.setOnClickListener {
            val intent = Intent(getActivity(), AddExpenseActivity::class.java)
            getActivity()?.startActivity(intent)
        }
//        viewModel.getGroupData(groupId).observe(viewLifecycleOwner) {
//            if (it != null) {
//
//            }
//        }

        viewModel.getGroupExpenses(groupId?.groupId).observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                val expensesList = it
                adapter.setExpenseList(expensesList)
            }
        }

    }
}