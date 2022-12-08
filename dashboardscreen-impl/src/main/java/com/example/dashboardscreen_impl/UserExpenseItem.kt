package com.example.dashboardscreen_impl

import com.google.firebase.Timestamp

internal data class UserExpenseItem(
    val dateCreated: String? = null,
    val description: String? = null,
    val groupId: String? = null,
    val simplifiedDebts: List<Debt>? = null,
    val totalAmount: Int? = null,
    val users: List<String>? = null
)

internal data class Debt(
    val from: String? = null,
    val to: String? = null,
    val amount: Int? = null
)