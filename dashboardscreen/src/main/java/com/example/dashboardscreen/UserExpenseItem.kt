package com.example.dashboardscreen

import com.google.firebase.Timestamp

data class UserExpenseItem(
    val dateCreated: Timestamp? = null,
    val description: String? = null,
    val groupId: String? = null,
    val simplifiedDebts: List<Debt>? = null,
    val totalAmount: Int? = null,
    val users: List<String>? = null
)

data class Debt(
    val from: String? = null,
    val to: String? = null,
    val amount: Int? = null
)