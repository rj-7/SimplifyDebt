package com.example.dashboardscreen_impl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

internal class AddExpenseViewModel(firebaseAuth: FirebaseAuth) : ViewModel() {
    companion object {
        fun provideFactory(firebaseAuth: FirebaseAuth) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                return AddExpenseViewModel(firebaseAuth) as T
            }
        }
    }

    private val db = Firebase.firestore
    val navigationLiveData = MutableLiveData<AddExpenseNavigationEvent>()
    var groupMembers: List<UserServiceItem>? = null

    val expenseItem: UserExpenseItem = UserExpenseItem()

    fun postExpense(): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>(false)
        try {
            // Random rand = new Random();
            db.collection("Receipts").add(expenseItem).addOnSuccessListener {
                    result.value = true
                }.addOnFailureListener { exception: java.lang.Exception ->
                    result.value = false
                }
        } catch (e: Exception) {
            result.value = false
        }
        return result
    }

    fun getUsers(userIds: List<String>?): LiveData<List<UserServiceItem>?> {
        val result = MutableLiveData<List<UserServiceItem>?>()
        val docRef = db.collection("Users")
        docRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val friends = it.result.documents.filter {
                    userIds?.contains(it.data?.get("id")) == true
                }
                result.value = friends.mapNotNull { it.toObject(UserServiceItem::class.java) }
            } else {
                result.value = null
                Log.d("", "AddExpenseFragment, get failed with ", it.exception)
            }
        }
        return result
    }

    sealed class AddExpenseNavigationEvent {
        data class GoToAddExpense(val friendIds: String?) : AddExpenseNavigationEvent()
        data class GoToSelectUsers(val amount: Int) : AddExpenseNavigationEvent()
        object GoBack : AddExpenseNavigationEvent()
    }
}