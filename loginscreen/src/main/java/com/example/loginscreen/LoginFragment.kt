package com.example.loginscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.loginscreen.databinding.FragmentLoginBinding

internal class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment().apply {

        }
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding!!
    val viewModel: AuthViewModel by activityViewModels<AuthViewModel> {
        AuthViewModel.provideFactory(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signLinkText.setOnClickListener {
            viewModel.navigationLiveData.postValue(AuthViewModel.NavigationEvent.GoToRegister)
        }
    }
}