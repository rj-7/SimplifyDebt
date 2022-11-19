package com.example.dashboardscreen

data class UserItem(
    val userId: Int? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val phoneNo: String? = null,
    val email: String? = null,
    val friends: List<Int>? = null,
    val groups: List<Int>? = null
)