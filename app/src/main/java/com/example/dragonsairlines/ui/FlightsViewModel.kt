package com.example.dragonsairlines.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.Flight
import com.example.dragonsairlines.framework.datasource.api.util.NoConnectivityException
import com.example.dragonsairlines.framework.datasource.models.FlightUIModel
import com.example.dragonsairlines.framework.datasource.models.toPresentationModel
import com.example.usecases.GetFlights
import com.example.usecases.GetFlightsByPrice
import kotlinx.coroutines.launch


class FlightsViewModel(
    private val getFlights: GetFlights,
    private val getFlightsByPrice: GetFlightsByPrice
) : ViewModel() {

    private val _flights = MutableLiveData<List<FlightUIModel>>()
    val flights: LiveData<List<FlightUIModel>>
        get() = _flights

    private val _userNotificationMessage = MutableLiveData<String>()
    val userNotificationMessage: LiveData<String>
        get() = _userNotificationMessage

    fun getFlightsList() {
        viewModelScope.launch {
            try {
                _flights.value = getFlights.invoke().map(Flight::toPresentationModel)
            } catch (e: Exception) {
                if (e is NoConnectivityException) {
                    _userNotificationMessage.value = e.message
                }
            }
        }
    }

    fun filterFlights(fromPrice: String?, toPrice: String?) {
        val from = fromPrice?.toDoubleOrNull() ?: 0.0
        val to = toPrice?.toDoubleOrNull() ?: Double.MAX_VALUE
        _flights.value = getFlightsByPrice.invoke(from, to).map(Flight::toPresentationModel)
    }
}