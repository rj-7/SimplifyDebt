package com.example.dashboardscreen_impl

import android.app.Activity
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
            }
        }
    }

    private var _binding: FragmentGroupDetailsBinding? = null
    private val binding: FragmentGroupDetailsBinding
        get() = _binding!!
    private val viewModel: DashboardViewModel by activityViewModels<DashboardViewModel> {
        DashboardViewModel.provideFactory(FirebaseAuth.getInstance())
    }
    private var groupItem: GroupItem? = null
    private var adapter: ExpenseListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGroupDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            setGroupExpenses()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        groupItem = arguments?.getParcelable(GROUP_USER_ID) as GroupItem?
        adapter = ExpenseListAdapter(requireContext()) {

        }
        binding.expensesRv.adapter = adapter
        binding.expensesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.groupName.text = groupItem?.groupDescription
        binding.initialsTextView.text =
            groupItem?.groupName
        binding.amountText.text = "$50"

        binding.buttonAddExpense.setOnClickListener {
            val intent = AddExpenseActivity.getIntent(requireActivity(), groupItem!!)
            this.startActivityForResult(intent, 100)
        }

        setGroupExpenses()
    }

    private fun setGroupExpenses() {
        viewModel.getGroupExpenses(groupItem?.groupId).observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                val expensesList = it
                adapter?.setExpenseList(expensesList)
                val userBalance =
                    viewModel.totalBalance(it, FirebaseAuth.getInstance().currentUser?.uid)
                binding.amountText.text = "$" + userBalance.totalBalance.toString()
            }
        }
    }
}