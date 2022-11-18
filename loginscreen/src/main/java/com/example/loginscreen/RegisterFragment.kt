package com.example.loginscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.loginscreen.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    companion object {
        fun newInstance() = RegisterFragment().apply {

        }
    }

    private var _binding: FragmentRegisterBinding? = null
    private val binding: FragmentRegisterBinding
        get() = _binding!!
    val viewModel: AuthViewModel by viewModels<AuthViewModel> {
        requireActivity()
        AuthViewModel.provideFactory(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}