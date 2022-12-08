package com.example.dashboardscreen_impl

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class GroupItem(
    val groupDescription: String? = "",
    val groupName: String? = null,
    val members: List<String>? = null,
    val groupId: String? = null
) : Parcelable
