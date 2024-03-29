package com.example.dashboardscreen_impl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.dashboardscreen.UserBalanceService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.abs

internal class DashboardViewModel(firebaseAuth: FirebaseAuth) : ViewModel() {

    companion object {
        //PATTERN: FACTORY
        //Used to initialise a viewmodel instance. Android activities have Default viewmodel providers
        //View Model providers have a default factory implementation. We are overriding that to create a new viewmodel instance with parameters
        fun provideFactory(firebaseAuth: FirebaseAuth) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                return DashboardViewModel(firebaseAuth) as T
            }
        }
    }

    var userData: UserServiceItem? = null

    //PATTERN: OBSERVABLE
    //https://developer.android.com/topic/libraries/architecture/livedata
    //Livedata in android are observables that respect component lifecycle
    //In our project we have used them as event data, use them to store data from network request (db tables)
    // They are observed in activities/fragments
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

    //PATTERN: OBSERVABLE
    //https://developer.android.com/topic/libraries/architecture/livedata
    //Livedata in android are observables that respect component lifecycle
    //In our project we have used them as event data, use them to store data from network request (db tables)
    // They are observed in activities/fragments
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

    //PATTERN: OBSERVABLE
    //https://developer.android.com/topic/libraries/architecture/livedata
    //Livedata in android are observables that respect component lifecycle
    //In our project we have used them as event data, use them to store data from network request (db tables)
    // They are observed in activities/fragments
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

    fun getGroupExpenses(groupId: String?): LiveData<List<UserExpenseItem>?> {
        val result = MutableLiveData<List<UserExpenseItem>?>()
        val docRef = fireStoreDb.collection("Receipts")
        docRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val expenses = it.result.documents.filter {
                    val groupInfo = it.data?.get("groupId") as String?
                    groupInfo == groupId
                    // users?.contains(groupId) == true
                }
                result.value = expenses.mapNotNull { it.toObject(UserExpenseItem::class.java) }
            } else {
                result.value = null
                Log.d("DashboardFragment", "get failed with ", it.exception)
            }
        }
        return result
    }

    fun totalBalance(userExpenses: List<UserExpenseItem>?, userId: String?): UserBalanceService {

        var positiveAmt: MutableList<Int> = mutableListOf<Int>()
        var negativeAmt: MutableList<Int> = mutableListOf<Int>()
        userExpenses?.forEach { x ->
            x.simplifiedDebts?.forEach { y ->
                if (y.to == userId) y.amount?.let {
                    positiveAmt.add(
                        it
                    )
                }
            }
        }
        userExpenses?.forEach { x ->
            x.simplifiedDebts?.forEach { y ->
                if (y.from == userId) y.amount?.let {
                    negativeAmt.add(
                        it
                    )
                }
            }
        }
        val owed = positiveAmt.fold(0) { total, it -> total + it }
        val owe = negativeAmt.fold(0) { total, it -> total - it }
        val totalBalance = owed + owe
        val ans = UserBalanceService(userId, owed, abs(owe), totalBalance)
        return ans
    }

    fun BalanceWithFriends(
        userExpenses: List<UserExpenseItem>?,
        userId: String?,
        friendId: String?
    ): UserBalanceService {

        var positiveAmt: MutableList<Int> = mutableListOf<Int>()
        var negativeAmt: MutableList<Int> = mutableListOf<Int>()
        userExpenses?.forEach { x ->
            x.simplifiedDebts?.forEach { y ->
                if (y.to == userId && y.from == friendId) y.amount?.let {
                    positiveAmt.add(
                        it
                    )
                }
            }
        }
        userExpenses?.forEach { x ->
            x.simplifiedDebts?.forEach { y ->
                if (y.from == userId && y.to == friendId) y.amount?.let {
                    negativeAmt.add(
                        it
                    )
                }
            }
        }
        val owed = positiveAmt.fold(0) { total, it -> total + it }
        val owe = negativeAmt.fold(0) { total, it -> total - it }
        val totalBalance = owed + owe
        val ans = UserBalanceService(userId, owed, abs(owe), abs(totalBalance))
        return ans
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

    fun getGroups(userId: String): LiveData<List<GroupItem>?> {
        val result = MutableLiveData<List<GroupItem>?>()
        val docRef = fireStoreDb.collection("Groups")
        docRef.get().addOnCompleteListener { it ->
            if (it.isSuccessful) {
                val groups = it.result.documents.filter { document ->
                    val groupsObjects = document.data?.get("members") as List<String>?
                    groupsObjects?.contains(userId) == true
                }
                result.value =
                    groups.mapNotNull { doc -> doc.toObject(GroupItem::class.java) }
            } else {
                result.value = null
                Log.d("DashboardFragment", "get failed with ", it.exception)
            }
        }
        return result
    }

    sealed class DashboardNavigationEvent {
        data class GoToFriendDetails(val friendIds: String?) : DashboardNavigationEvent()
        data class GoToGroupDetails(val groupItem: GroupItem) : DashboardNavigationEvent()
        data class GoToSettle(val yourName: String?, val friendName: String?, val amount: String?) :
            DashboardNavigationEvent()

        data class GoToSuccess(val friendName: String?) : DashboardNavigationEvent()
    }

}