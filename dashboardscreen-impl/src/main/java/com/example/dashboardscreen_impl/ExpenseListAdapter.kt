package com.example.dashboardscreen_impl

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dashboardscreen_impl.databinding.ItemExpenseBinding

internal class ExpenseListAdapter(
    val context: Context,
    val onItemClicked: (item: UserExpenseItem) -> Unit
) : RecyclerView.Adapter<ExpenseListAdapter.ExpenseListViewHolder>() {

    private var expenseList: List<UserExpenseItem>? = null

    fun setExpenseList(list: List<UserExpenseItem>?) {
        expenseList = list ?: emptyList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseListViewHolder {
        val binding = ItemExpenseBinding.inflate(LayoutInflater.from(context), parent, false)
        return ExpenseListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseListViewHolder, position: Int) {
        val item = expenseList?.get(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount() = expenseList?.size ?: 0

    inner class ExpenseListViewHolder(private val binding: ItemExpenseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserExpenseItem) {
            binding.expenseNameText.text = item.description
            binding.amountText.text = "$" + item.totalAmount.toString()
            binding.itemRootView.setOnClickListener {
                onItemClicked(item)
            }
        }
    }
}