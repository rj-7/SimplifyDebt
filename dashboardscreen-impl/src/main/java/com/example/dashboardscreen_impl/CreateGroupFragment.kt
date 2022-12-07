package com.example.dashboardscreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.dashboardscreen_impl.DashboardActivity
import com.example.dashboardscreen_impl.DashboardViewModel
import com.example.dashboardscreen_impl.databinding.FragmentCreateGroupBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.HashMap

internal class CreateGroupFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    companion object {
        fun getInstance() = CreateGroupFragment().apply {
            Bundle().apply {
            }
        }
    }
    private var _binding: FragmentCreateGroupBinding? = null
    private val binding: FragmentCreateGroupBinding
        get() = _binding!!

    private val viewModel: DashboardViewModel by activityViewModels<DashboardViewModel> {
        DashboardViewModel.provideFactory(FirebaseAuth.getInstance())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateGroupBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // val userData = viewModel.userData
        db = FirebaseFirestore.getInstance()
        binding.apply {
//            initialsTextView.text =
//                "${userData?.firstName?.get(0)}${userData?.lastName?.get(0)}".toUpperCase()
//            userNameTextView.text = "${userData?.firstName} ${userData?.lastName}"
            createGroupButton.setOnClickListener {
                    view: View? -> store()
            }
        }

    }

    private fun store() {
        val groupNameBfr = binding.name
        val groupDescriptionBfr = binding.description

        var groupName = groupNameBfr.text.toString().trim()
        val groupDescription = groupDescriptionBfr.text.toString().trim()
        val members = Arrays.asList(  FirebaseAuth.getInstance().currentUser?.uid)



        if (!groupName.isEmpty() && !groupDescription.isEmpty()) {
            try {
                // Random rand = new Random();
                val items = HashMap<String, Any>()
                items.put("groupName", groupName)
                items.put("groupDescription", groupDescription)
                items.put("members",members)
                db.collection("Groups").add(items).addOnSuccessListener {
                        documentReference -> Toast.makeText(requireContext(), "Successfully uploaded to the database :)", Toast.LENGTH_LONG).show()
                    val intent = Intent(activity, DashboardActivity::class.java)
                    startActivity(intent)
                }.addOnFailureListener {
                        exception: java.lang.Exception -> Toast.makeText(requireContext(), exception.toString(), Toast.LENGTH_LONG).show()
                }
            }catch (e:Exception) {
                Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show()
            }
        }else {
            Toast.makeText(requireContext(), "Please fill up the fields :(", Toast.LENGTH_LONG).show()
        }
    }


}