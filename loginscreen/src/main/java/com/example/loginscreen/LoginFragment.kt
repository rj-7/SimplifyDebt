package com.example.loginscreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.loginscreen.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

internal class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment().apply {

        }
    }

    private lateinit var firebaseAuth: FirebaseAuth
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
       // setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.signLinkText.setOnClickListener {
            val intent = Intent(activity, RegisterFragment::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.loginUserName.text.toString()
            val pass = binding.password.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(activity, AuthActivity::class.java)
                        startActivity(intent)
                    } else {

                        Toast.makeText(activity, "Please enter correct details", Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(activity, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signLinkText.setOnClickListener {
            viewModel.navigationLiveData.postValue(AuthViewModel.NavigationEvent.GoToRegister)
        }
    }
}