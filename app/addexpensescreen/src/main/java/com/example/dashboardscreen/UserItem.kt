package com.example.dashboardscreen

data class UserItem(
    val userId: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val phoneNo: String? = null,
    val email: String? = null,
    val friends: List<String>? = null,
    val groups: List<String>? = null
)