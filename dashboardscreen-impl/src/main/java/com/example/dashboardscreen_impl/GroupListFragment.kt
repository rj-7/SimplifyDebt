package com.example.dashboardscreen_impl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dashboardscreen_impl.databinding.FragmentGroupListBinding
import com.google.firebase.auth.FirebaseAuth

internal class GroupListFragment : Fragment() {
    companion object {
        fun newInstance() = GroupListFragment().apply {
            Bundle().apply {

            }
        }
    }

    private var _binding: FragmentGroupListBinding? = null
    private val binding: FragmentGroupListBinding
        get() = _binding!!
    private val viewModel: DashboardViewModel by activityViewModels<DashboardViewModel> {
        DashboardViewModel.provideFactory(FirebaseAuth.getInstance())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.groupListRV.layoutManager = LinearLayoutManager(requireContext())

        val adapter = GroupListAdapter(requireContext()) {
            viewModel.navigationLiveData.value =
                DashboardViewModel.DashboardNavigationEvent.GoToGroupDetails(it)
        }
        binding.groupListRV.adapter = adapter
        val userId = viewModel.userData?.id ?: ""
        viewModel.getGroups(userId.trim()).observe(viewLifecycleOwner) { groups ->
            if (groups != null) {
                adapter.setGroupsList(groups)
            }
        }
    }
}