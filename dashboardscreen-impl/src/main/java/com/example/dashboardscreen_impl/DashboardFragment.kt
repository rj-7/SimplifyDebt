package com.example.dashboardscreen_impl

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.dashboardscreen_impl.databinding.FragmentDashboardBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

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
    private val viewModel: DashboardViewModel by activityViewModels<DashboardViewModel> {
        DashboardViewModel.provideFactory(FirebaseAuth.getInstance())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
//                val store = getView()?.findViewById<View>(R.id.idBtnSubmitCourse) as Button
//
//        store.setOnClickListener {
//            val intent = Intent(getActivity(), AddExpenseActivity::class.java)
//            getActivity()?.startActivity(intent) }
//
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userData = viewModel.userData
        binding.apply {
            dashboardPager.adapter = DashboardPagerAdapter(requireActivity())
            TabLayoutMediator(tabLayout, dashboardPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "FRIENDS"
                    1 -> "GROUPS"
                    else -> "ACTIVITIES"
                }
            }.attach()
            initialsTextView.text =
                "${userData?.firstName?.get(0)}${userData?.lastName?.get(0)}".toUpperCase()
            userNameTextView.text = "${userData?.firstName} ${userData?.lastName}"
            addButton.setOnClickListener {
                val intent = Intent(getActivity(), AddExpenseActivity::class.java)
                getActivity()?.startActivity(intent) }
             viewModel.getUserExpenses(FirebaseAuth.getInstance().currentUser?.uid)
                        .observe(viewLifecycleOwner) { userExpenses ->
                            val userBalance = viewModel.totalBalance(userExpenses,FirebaseAuth.getInstance().currentUser?.uid)
                            totalPriceText.text= "$" + userBalance.totalBalance.toString()
                            owedPriceText.text= "$" + userBalance.owed.toString()
                            debtPriceText.text="$" + userBalance.owe.toString()
                        }
                }
            }


    }
