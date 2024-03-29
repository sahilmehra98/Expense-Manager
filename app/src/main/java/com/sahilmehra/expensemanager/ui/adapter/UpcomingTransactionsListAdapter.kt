package com.sahilmehra.expensemanager.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sahilmehra.expensemanager.R
import com.sahilmehra.expensemanager.data.TransactionMode
import com.sahilmehra.expensemanager.data.TransactionType
import com.sahilmehra.expensemanager.data.UpcomingTransaction
import com.sahilmehra.expensemanager.readableFormat
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.transactions_list_item.*

class UpcomingTransactionsListAdapter(
    private val context: Context,
    private val editListener: (Long) -> Unit,
    private val deleteListener: (UpcomingTransaction) -> Unit
) :
    ListAdapter<UpcomingTransaction, UpcomingTransactionsListAdapter.ViewHolder>(DiffCallbackUtList()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UpcomingTransactionsListAdapter.ViewHolder {
        val itemLayout =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.transactions_list_item, parent, false)
        return ViewHolder(itemLayout)
    }

    override fun onBindViewHolder(
        holder: UpcomingTransactionsListAdapter.ViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        init {
            //set the popup menu
            itemView.setOnClickListener {
                val popupMenu = PopupMenu(context, itemView)

                popupMenu.inflate(R.menu.upcoming_menu) //inflate the menu layout

                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        //mark the tranaction as completed
                        R.id.completeItem -> {
                            Log.e("upcoming value", "upcoming complete")
                            Toast.makeText(context, "Transaction Completed", Toast.LENGTH_SHORT)
                                .show()
                        }
                        //edit the transaction
                        R.id.editUpcomingItem -> {
                            editListener.invoke(getItem(adapterPosition).id)
                        }
                        //delete the transaction
                        R.id.deleteUpcomingItem -> {
                            deleteListener.invoke(getItem(adapterPosition))
                        }
                    }
                    true
                }

                popupMenu.show() //show the popup menu
            }
        }

        fun bind(upcomingTransaction: UpcomingTransaction) {
            //show data in text views
            with(upcomingTransaction) {
                tvNameList.text = name
                tvDateList.text = date.readableFormat()
                when (mode) {
                    0 -> tvTransactionTypeList.text = TransactionMode.Cash.name
                    1 -> tvTransactionTypeList.text = TransactionMode.Card.name
                    2 -> tvTransactionTypeList.text = TransactionMode.Cheque.name
                    3 -> tvTransactionTypeList.text = TransactionMode.Others.name
                }

                //if type is expense, mark its color as red, otherwise green
                if (type == TransactionType.Income.ordinal) {
                    tvAmountList.text = "+$amount"
                    tvAmountList.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.income_text_color
                        )
                    )
                } else {
                    tvAmountList.text = "-$amount"
                    tvAmountList.setTextColor(
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
class DiffCallbackUtList : DiffUtil.ItemCallback<UpcomingTransaction>() {
    override fun areItemsTheSame(
        oldItem: UpcomingTransaction,
        newItem: UpcomingTransaction
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: UpcomingTransaction,
        newItem: UpcomingTransaction
    ): Boolean {
        return oldItem == newItem
    }
}