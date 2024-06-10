package com.example.dragonsairlines.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dragonsairlines.databinding.ListViewRowBinding
import com.example.dragonsairlines.framework.datasource.models.FlightUIModel

class FlightsAdapter :
    RecyclerView.Adapter<FlightsAdapter.RowHolder>() {

    private val values: MutableList<FlightUIModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {

        val inflater = LayoutInflater.from(parent.context)
        val inflatedView = ListViewRowBinding.inflate(
            inflater,
            parent,
            false
        )
        return RowHolder(inflatedView)
    }

    fun updateView(values: List<FlightUIModel>) {
        val diffCallback = FlightDiffCallback(this.values, values)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.values.clear()
        this.values.addAll(values)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = values.size

    class RowHolder(binding: ListViewRowBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private var value: FlightUIModel? = null
        private val viewBinding = binding

        override fun onClick(v: View?) {
        }

        fun bindHolder(value: FlightUIModel) {
            this.value = value
            viewBinding.outcome.text = this.value?.from
            viewBinding.income.text = this.value?.to
            ("From: " + this.value?.totalPrice).also { viewBinding.price.text = it }
        }
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bindHolder(values[position])
    }
}