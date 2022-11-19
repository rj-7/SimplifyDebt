package com.example.loginscreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.dashboardscreen.DashboardActivity
import com.example.loginscreen.databinding.FragmentLoginBinding
import com.google.android.gms.auth.account.WorkAccount.getClient
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignIn.getClient
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

internal class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment().apply {

        }
        private const val TAG = "LoginActivity"
        private const val RC_GOOGLE_SIGN_IN = 4926
    }
    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
    private var showOneTapUI = true

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var oneTapClient: SignInClient
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var signInRequest: BeginSignInRequest
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

//        val gso =
//            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build()
//        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding.googleLogInButton.setOnClickListener {
//            val intent = Intent(activity, GoogleSigninActivity::class.java)
//            startActivity(intent)
            signInGoogle()
        }

        binding.loginButton.setOnClickListener {
            val email = binding.loginUserName.text.toString()
            val pass = binding.password.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                       // startActivity(DashboardActivity.getIntent(this))
                        //TODO giving circular dependency error
                        val intent = Intent(activity, DashboardActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
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


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        when (requestCode) {
//            REQ_ONE_TAP -> {
//                try {
//                    val googleCredential = oneTapClient.getSignInCredentialFromIntent(data)
//                    val idToken = googleCredential.googleIdToken
//                    when {
//                        idToken != null -> {
//                            // Got an ID token from Google. Use it to authenticate
//                            // with Firebase.
//                            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
//                            firebaseAuth.signInWithCredential(firebaseCredential)
//                                .addOnCompleteListener() { task ->
//                                    if (task.isSuccessful) {
//                                        // Sign in success, update UI with the signed-in user's information
//                                        Log.d(TAG, "signInWithCredential:success")
//                                        val user = firebaseAuth.currentUser
//                                        updateUI(user)
//                                    } else {
//                                        // If sign in fails, display a message to the user.
//                                        Log.w(TAG, "signInWithCredential:failure", task.exception)
//                                        updateUI(null)
//                                    }
//                                }
//                        }
//                        else -> {
//                            // Shouldn't happen.
//                            Log.d(TAG, "No ID token!")
//                        }
//                    }
//                } catch (e: ApiException) {
//                    // ...
//                }
//            }
//        }
//        // ...
//    }
//
//    private fun updateUI(user: FirebaseUser?) {
//        if (user == null) {
//            Log.w(TAG, "user not signed in..")
//            return
//        }
//        val intent = Intent(activity, DashboardActivity::class.java)
//        startActivity(intent)
//        //finish()
//        // Navigate to MainActivity
//    }

    private fun signInGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
            }
        }else{
            Toast.makeText(activity, task.exception.toString() , Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken , null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                val intent : Intent = Intent(activity , DashboardActivity::class.java)
                //intent.putExtra("email" , account.email)
                //intent.putExtra("name" , account.displayName)
                startActivity(intent)
            }else{
                Toast.makeText(activity, it.exception.toString() , Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signLinkText.setOnClickListener {
            viewModel.navigationLiveData.postValue(AuthViewModel.NavigationEvent.GoToRegister)
        }
    }
}