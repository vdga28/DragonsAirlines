package com.example.dragonsairlines.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.dragonsairlines.framework.datasource.models.FlightUIModel

class FlightDiffCallback(val oldList: MutableList<FlightUIModel>, val newList: List<FlightUIModel>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.to == newItem.to &&
                oldItem.from == newItem.from &&
                oldItem.totalPrice == newItem.totalPrice
    }
}
