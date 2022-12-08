package com.example.dashboardscreen_impl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.dashboardscreen_impl.databinding.FragmentSettleBinding
import com.google.firebase.auth.FirebaseAuth

internal class SettleUpFragment : Fragment() {

    companion object {

        const val YOUR_NAME = "YOUR_NAME"
        const val FRIEND_NAME = "FRIEND_NAME"
        const val AMOUNT = "AMOUNT"

        fun newInstance(yourName: String?, friendName: String?, amount: String?) =
            SettleUpFragment().apply {
                arguments = Bundle().apply {
                    putString(YOUR_NAME, yourName)
                    putString(FRIEND_NAME, friendName)
                    putString(AMOUNT, amount)
                }
            }
    }

    private var _binding: FragmentSettleBinding? = null
    private val binding: FragmentSettleBinding
        get() = _binding!!
    private val viewModel: DashboardViewModel by activityViewModels<DashboardViewModel> {
        DashboardViewModel.provideFactory(FirebaseAuth.getInstance())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val yourName = arguments?.getString(YOUR_NAME)
        val friendName = arguments?.getString(FRIEND_NAME)
        val amount = arguments?.getString(AMOUNT)

        binding.apply {
            settleWithText.text = "You owe ${friendName} ${amount}"
            senderImg.text = yourName?.get(0)?.uppercase()
            receiverImg.text = friendName?.get(0)?.uppercase()

            settleBtn.setOnClickListener {
                viewModel.navigationLiveData.value =
                    DashboardViewModel.DashboardNavigationEvent.GoToSuccess(friendName)
            }
        }
    }
}