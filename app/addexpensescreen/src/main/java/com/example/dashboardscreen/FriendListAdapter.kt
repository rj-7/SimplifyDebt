package com.example.dashboardscreen

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardscreen.databinding.ItemFriendListBinding

class FriendListAdapter(
    val context: Context,
    val onItemClicked: (item: UserServiceItem) -> Unit
) :
    RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder>() {

    private var friendList: List<UserServiceItem>? = null

    fun setUserList(list: List<UserServiceItem>?) {
        friendList = list ?: emptyList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListViewHolder {
        val binding = ItemFriendListBinding.inflate(LayoutInflater.from(context), parent, false)
        return FriendListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendListViewHolder, position: Int) {
        val item = friendList?.get(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount() = friendList?.size ?: 0

    inner class FriendListViewHolder(private val binding: ItemFriendListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserServiceItem) {
            binding.friendName.text = "${item.firstName} ${item.lastName}"
            binding.debtText.text = "Owes you"
            binding.amountText.text = "$30"
            binding.initialsTextView.text =
                "${item.firstName?.get(0)}${item.lastName?.get(0)}".toUpperCase()
            binding.itemRootView.setOnClickListener {
                onItemClicked(item)
            }
        }
    }
}