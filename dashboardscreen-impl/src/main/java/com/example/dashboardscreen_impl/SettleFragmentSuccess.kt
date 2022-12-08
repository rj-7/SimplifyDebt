package com.example.dashboardscreen_impl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dashboardscreen_impl.databinding.SettleUpSuccesBinding

class SettleFragmentSuccess : Fragment() {
    companion object {
        const val FRIEND_NAME = "FRIEND_NAME"

        fun newInstance(friendName: String?) =
            SettleFragmentSuccess().apply {
                arguments = Bundle().apply {
                    putString(FRIEND_NAME, friendName)
                }
            }
    }

    private var _binding: SettleUpSuccesBinding? = null
    private val binding: SettleUpSuccesBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SettleUpSuccesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val friendName = arguments?.getString(FRIEND_NAME)
        binding.apply {
            settleWithText.text = "Your debts with  ${friendName} has been settled up"
        }
    }
}