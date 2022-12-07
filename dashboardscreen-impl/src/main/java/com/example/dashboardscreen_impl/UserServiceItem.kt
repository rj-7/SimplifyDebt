package com.example.dashboardscreen_impl

internal data class UserServiceItem(
    val id: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val friends: List<String>? = null,
    val groups: List<String>? = null,
    val emailId: String? = null
)