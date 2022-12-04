package com.example.dashboardscreen

data class ExpenseItem(
    val receiptId: String? = null,
    val description: String? = null,
    val totalAmount: String? = null,
    val receiptDate: String? = null,
    val groupId: String?= null,
    val paidBy: List<UserItem>? = null,
    val splitTo: List<UserItem>? = null
)