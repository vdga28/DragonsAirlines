package com.example.dragonsairlines.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dragonsairlines.databinding.ActivityFlightsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class FlightsActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: FlightsAdapter
    private lateinit var binding : ActivityFlightsBinding

    val viewModel: FlightsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlightsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        linearLayoutManager = LinearLayoutManager(this)
        initViewModel()
        initRecycler()
    }

    private fun initRecycler() {
        adapter = FlightsAdapter()
        binding.apply {
            flightsView.layoutManager = linearLayoutManager
            flightsView.adapter = adapter
        }
    }

    private fun initViewModel() {
        viewModel.flights.observe(this) {
            adapter.updateView(it)
            binding.progressBar.visibility = View.GONE
        }

        viewModel.userNotificationMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            binding.progressBar.visibility = View.GONE
        }

        binding.btnSearchFlights.setOnClickListener {
            val fromPrice = binding.etFromPrice.text.toString()
            val toPrice = binding.etToPrice.text.toString()
            viewModel.filterFlights(fromPrice, toPrice)
            this.hideKeyboard()
        }
        viewModel.getFlightsList()
        binding.progressBar.visibility = View.VISIBLE
    }
}