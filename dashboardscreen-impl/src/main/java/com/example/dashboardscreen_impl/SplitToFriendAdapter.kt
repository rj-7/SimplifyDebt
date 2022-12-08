package com.example.dashboardscreen_impl

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardscreen_impl.databinding.ItemSplitToFriendBinding

//PATTERN: ADAPTER PATTERN
// To display a list of row objects, A List View requires view bindings and view holders
// However we have these lists in the form of lists of data objects
//Adapter wraps/converts these objects in/to view holders that is required by list view
internal class SplitToFriendAdapter(
    val context: Context,
    val onItemClicked: (item: UserServiceItem, amount: Int) -> Unit
) : RecyclerView.Adapter<SplitToFriendAdapter.SplitToFriendViewHolder>() {

    private var friendList: List<UserServiceItem>? = null

    fun setUserList(list: List<UserServiceItem>?) {
        friendList = list ?: emptyList()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SplitToFriendViewHolder {
        val binding = ItemSplitToFriendBinding.inflate(LayoutInflater.from(context), parent, false)
        return SplitToFriendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SplitToFriendViewHolder, position: Int) {
        val item = friendList?.get(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount() = friendList?.size ?: 0

    inner class SplitToFriendViewHolder(private val binding: ItemSplitToFriendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserServiceItem) {
            binding.friendName.text = "${item.firstName} ${item.lastName}"
            binding.amountCheckbox.setOnClickListener {
                val amount = binding.amountEditText.text.toString()
                if (amount.isNullOrBlank()) {
                    binding.amountCheckbox.isChecked = false
                } else onItemClicked(item, amount.toInt())
            }
            binding.initialsTextView.text =
                "${item.firstName?.get(0)}${item.lastName?.get(0)}".toUpperCase()
        }
    }
}