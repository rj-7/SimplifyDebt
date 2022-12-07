package com.example.dashboardscreen_impl

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dashboardscreen_impl.databinding.FragmentAddexpenseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.HashMap

internal class AddExpenseFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore

    companion object {
        fun getInstance() = AddExpenseFragment().apply {
            Bundle().apply {

            }
        }
    }
    private var _binding: FragmentAddexpenseBinding? = null
    private val binding: FragmentAddexpenseBinding
        get() = _binding!!
//    private val viewModel: DashboardViewModel by activityViewModels<DashboardViewModel> {
//        DashboardViewModel.provideFactory(FirebaseAuth.getInstance())
//    }


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
       // val userData = viewModel.userData
        db = FirebaseFirestore.getInstance()
        binding.apply {
//            initialsTextView.text =
//                "${userData?.firstName?.get(0)}${userData?.lastName?.get(0)}".toUpperCase()
//            userNameTextView.text = "${userData?.firstName} ${userData?.lastName}"
            addExpenseButton.setOnClickListener {
                    view: View? -> store()
        }
    }

}

    private fun store() {
        val expenseNameBfr = binding.name
        val amountBfr = binding.amount
        val dateBfr = binding.date

        var expenseName = expenseNameBfr.text.toString().trim()
        val amount = amountBfr.text.toString().trim()
        val date = dateBfr.text.toString().trim()

        if (!expenseName.isEmpty() && !amount.isEmpty() && !date.isEmpty()) {
            try {
               // Random rand = new Random();
                val items = HashMap<String, Any>()
                items.put("expenseName", expenseName)
                items.put("amount", amount)
                items.put("date", date)
                db.collection("test").document("Receipts").set(items).addOnSuccessListener {
                        void: Void? -> Toast.makeText(requireContext(), "Successfully uploaded to the database :)", Toast.LENGTH_LONG).show()
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