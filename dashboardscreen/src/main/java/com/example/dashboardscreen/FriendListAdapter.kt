package com.example.dashboardscreen

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardscreen.databinding.ItemFriendListBinding

class FriendListAdapter(
    val context: Context,
    val onItemClicked: (item: UserItem) -> Unit
) :
    RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder>() {

    private lateinit var friendList: List<UserItem>

    fun setUserList(list: List<UserItem>?) {
        friendList = list ?: emptyList()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListViewHolder {
        val binding = ItemFriendListBinding.inflate(LayoutInflater.from(context), parent, false)
        return FriendListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendListViewHolder, position: Int) {
        holder.bind(friendList.get(position))
    }

    override fun getItemCount() = friendList.size

    inner class FriendListViewHolder(val binding: ItemFriendListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserItem) {
            binding.friendName.text = "${item.firstName} ${item.lastName}"
            binding.debtText.text = "Owes you"
            binding.amountText.text = "$100"
            binding.itemRootView.setOnClickListener {
                onItemClicked(item)
            }
        }
    }
}