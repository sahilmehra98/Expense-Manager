package com.sahilmehra.expensemanager.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sahilmehra.expensemanager.R
import com.sahilmehra.expensemanager.data.PastTransaction
import com.sahilmehra.expensemanager.data.TransactionType
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.month_transactions_list_item.*

class MonthsInnerListAdapter(
    private val context: Context
) :
    ListAdapter<PastTransaction, MonthsInnerListAdapter.ViewHolder>(DiffCallbackMt()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MonthsInnerListAdapter.ViewHolder {
        val itemLayout =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.month_transactions_list_item, parent, false)
        return ViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: MonthsInnerListAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return if(currentList.size<=3)
            super.getItemCount()
        else
            3
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(pastTransaction: PastTransaction) {
            //populate the ui with data
            with(pastTransaction) {
                tvTransactionName.text = name

                //if type is expense, mark its color as red, otherwise green
                if (type == TransactionType.Income.ordinal) {
                    tvTransactionAmount.text = "+$amount"
                    tvTransactionAmount.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.income_text_color
                        )
                    )
                } else {
                    tvTransactionAmount.text = "-$amount"
                    tvTransactionAmount.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.expense_text_color
                        )
                    )
                }
            }
        }
    }
}

//check if the list has been updated or not
class DiffCallbackMt : DiffUtil.ItemCallback<PastTransaction>() {
    override fun areItemsTheSame(oldItem: PastTransaction, newItem: PastTransaction): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PastTransaction, newItem: PastTransaction): Boolean {
        return oldItem == newItem
    }
}