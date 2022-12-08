package com.example.dashboardscreen_impl

internal data class UserExpenseItem(
    var dateCreated: String? = null,
    var description: String? = "",
    var groupId: String? = null,
    var simplifiedDebts: List<Debt>? = null,
    var totalAmount: Int? = null,
    var users: List<String>? = null
)

internal data class Debt(
    var from: String? = null,
    var to: String? = null,
    var amount: Int? = null
)