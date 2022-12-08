package com.example.dashboardscreen_impl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.dashboardscreen_impl.databinding.FragmentAddexpenseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

internal class AddExpenseFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore

    companion object {
        const val GROUP_ITEM = "GROUP_ITEM"
        fun getInstance(groupItem: GroupItem?) = AddExpenseFragment().apply {
            arguments = Bundle().apply {
                putParcelable(GROUP_ITEM, groupItem)
            }
        }
    }

    private var _binding: FragmentAddexpenseBinding? = null
    private val binding: FragmentAddexpenseBinding
        get() = _binding!!
    private val viewModel: AddExpenseViewModel by activityViewModels<AddExpenseViewModel> {
        AddExpenseViewModel.provideFactory(FirebaseAuth.getInstance())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddexpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        val group = arguments?.getParcelable(GROUP_ITEM) as GroupItem?
        binding.apply {
            withText.text = "With group ${group?.groupName}"
        }

        viewModel.getUsers(group?.members).observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                viewModel.groupMembers = it
            }
        }

        binding.addSplits.setOnClickListener {
            val amount = binding.amount.text.toString()
            if (!amount.isNullOrBlank()) {
                viewModel.navigationLiveData.value =
                    AddExpenseViewModel.AddExpenseNavigationEvent.GoToSelectUsers(amount.toInt())
            }
        }
        binding.addExpenseButton.setOnClickListener {
            val expenseNameBfr = binding.name
            val amountBfr = binding.amount
            val dateBfr = binding.date

            val expenseName = expenseNameBfr.text.toString().trim()
            val amount = amountBfr.text.toString().trim()
            val date = dateBfr.text.toString().trim()
            if (!expenseName.isEmpty() && !amount.isEmpty() && !date.isEmpty()) {
                viewModel.expenseItem.apply {
                    this.dateCreated = date
                    this.groupId = group?.groupId
                    this.totalAmount = amount.toInt()
                    this.description = expenseName
                }
                viewModel.postExpense().observe(viewLifecycleOwner) {
                    if (it) {
                        viewModel.navigationLiveData.value =
                            AddExpenseViewModel.AddExpenseNavigationEvent.GoBack
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please fill up the fields :(",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }

    }
}