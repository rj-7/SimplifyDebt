package com.example.dashboardscreen_impl

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardscreen_impl.databinding.ItemFriendListBinding

internal class GroupListAdapter(
    val context: Context,
    val onItemClicked: (item: GroupItem) -> Unit
) :
    RecyclerView.Adapter<GroupListAdapter.GroupListViewHolder>() {
    private var groupItemList: List<GroupItem>? = null

    fun setGroupsList(list: List<GroupItem>?) {
        groupItemList = list ?: emptyList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupListViewHolder {
        val binding = ItemFriendListBinding.inflate(LayoutInflater.from(context), parent, false)
        return GroupListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupListViewHolder, position: Int) {
        val item = groupItemList?.get(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount() = groupItemList?.size ?: 0

    inner class GroupListViewHolder(private val binding: ItemFriendListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GroupItem) {
            binding.friendName.text = item.groupName
            binding.debtText.text = item.groupDescription
            binding.amountText.visibility = View.GONE
            binding.initialsTextView.visibility = View.GONE
            binding.itemRootView.setOnClickListener {
                onItemClicked(item)
            }
        }
    }
}