package com.example.dragonsairlines.framework.datasource.models

import com.example.domain.entities.Flight

data class FlightUIModel (val from: String,
                          val to: String,
                          val totalPrice: String)

fun Flight.toPresentationModel(): FlightUIModel =
    FlightUIModel(
        """${this.inbound.origin} -> ${this.inbound.destination}""",
        """${this.outbound.origin} -> ${this.outbound.destination}""",
        this.toSelectedCurrencyPrice()
    )

private fun Flight.toSelectedCurrencyPrice(): String {
    return """${this.price} ${this.currency}"""
}