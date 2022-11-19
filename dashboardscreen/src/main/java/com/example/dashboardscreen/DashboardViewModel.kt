package com.example.dashboardscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DashboardViewModel(firebaseAuth: FirebaseAuth) : ViewModel() {

    companion object {
        fun provideFactory(firebaseAuth: FirebaseAuth) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                return DashboardViewModel(firebaseAuth) as T
            }
        }
    }

    var userData: UserServiceItem? = null
    val navigationLiveData = MutableLiveData<DashboardNavigationEvent>()
    private val fireStoreDb = Firebase.firestore

    fun getUserData(userId: String?): LiveData<UserServiceItem?> {
        val result = MutableLiveData<UserServiceItem?>()
        val docRef = userId?.let { fireStoreDb.collection("Users").document(it) }
        docRef?.get()?.addOnCompleteListener {
            if (it.isSuccessful) {
                result.value = it.result.toObject(UserServiceItem::class.java)
            } else {
                result.value = null
                Log.d("DashboardFragment", "get failed with ", it.exception)
            }
        }
        return result
    }

    fun getFriendsData(friendIds: List<String>?): LiveData<List<UserServiceItem>?> {
        val result = MutableLiveData<List<UserServiceItem>?>()
        val docRef = fireStoreDb.collection("Users")
        docRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val friends = it.result.documents.filter {
                    friendIds?.contains(it.data?.get("id")) == true
                }
                result.value = friends.mapNotNull { it.toObject(UserServiceItem::class.java) }
            } else {
                result.value = null
                Log.d("DashboardFragment", "get failed with ", it.exception)
            }
        }
        return result
    }

    fun getUserExpenses(userId: String?): LiveData<List<UserExpenseItem>?> {
        val result = MutableLiveData<List<UserExpenseItem>?>()
        val docRef = fireStoreDb.collection("Receipts")
        docRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val expenses = it.result.documents.filter {
                    val users = it.data?.get("users") as List<String>?
                    users?.contains(userId) == true
                }
                result.value = expenses.mapNotNull { it.toObject(UserExpenseItem::class.java) }
            } else {
                result.value = null
                Log.d("DashboardFragment", "get failed with ", it.exception)
            }
        }
        return result
    }

    fun getFriendExpenses(
        userExpenses: List<UserExpenseItem>?,
        friendUsers: List<UserServiceItem>
    ): List<UserExpenseItem> {
        val friendExpenses = ArrayList<UserExpenseItem>()
        friendUsers.forEach { friend ->
            userExpenses?.filter { it.users?.contains(friend.id) == true }
                ?.let { friendExpenses.addAll(it) }
        }
        return friendExpenses
    }

    fun getFriendExpenses(
        userExpenses: List<UserExpenseItem>? = null,
        friendId: String? = null
    ): List<UserExpenseItem>? {
        return userExpenses?.filter {
            it.users?.contains(friendId) == true
        }
    }

    sealed class DashboardNavigationEvent {
        data class GoToFriendDetails(val friendIds: String?) : DashboardNavigationEvent()
    }

}