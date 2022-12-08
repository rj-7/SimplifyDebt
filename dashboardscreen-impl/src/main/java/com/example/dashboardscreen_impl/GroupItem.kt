package com.example.dashboardscreen_impl

import android.os.Parcel
import android.os.Parcelable

internal data class GroupItem(
    val groupDescription: String? = null,
    val groupName: String? = null,
    val members: List<String>? = null,
    val groupId: String? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(groupDescription)
        parcel.writeString(groupName)
        parcel.writeStringList(members)
        parcel.writeString(groupId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GroupItem> {
        override fun createFromParcel(parcel: Parcel): GroupItem {
            return GroupItem(parcel)
        }

        override fun newArray(size: Int): Array<GroupItem?> {
            return arrayOfNulls(size)
        }
    }
}