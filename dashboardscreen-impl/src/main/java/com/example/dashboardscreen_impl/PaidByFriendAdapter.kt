package com.example.dashboardscreen_impl

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardscreen_impl.databinding.ItemPaidByFriendBinding

internal class PaidByFriendAdapter(
    val context: Context
) : RecyclerView.Adapter<PaidByFriendAdapter.PaidByFriendViewHolder>() {

    private var friendList: List<UserServiceItem>? = null
    private var selectedFriend: UserServiceItem? = null

    fun setUserList(list: List<UserServiceItem>?) {
        friendList = list ?: emptyList()
        notifyDataSetChanged()
    }

    fun getSelectedFriend(): UserServiceItem? = selectedFriend

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaidByFriendViewHolder {
        val binding = ItemPaidByFriendBinding.inflate(LayoutInflater.from(context), parent, false)
        return PaidByFriendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PaidByFriendViewHolder, position: Int) {
        val item = friendList?.get(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount() = friendList?.size ?: 0

    inner class PaidByFriendViewHolder(private val binding: ItemPaidByFriendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserServiceItem) {
            binding.friendName.text = "${item.firstName} ${item.lastName}"
            binding.amountChecbox.isChecked = selectedFriend?.id.equals(item.id, true)
            binding.amountChecbox.setOnClickListener {
                selectedFriend = item
                notifyDataSetChanged()
            }
            binding.initialsTextView.text =
                "${item.firstName?.get(0)}${item.lastName?.get(0)}".toUpperCase()
        }
    }
}