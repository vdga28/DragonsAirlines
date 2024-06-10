package com.example.data

import java.math.RoundingMode

fun Double.toTwoDigitsDouble(): Double = this.toBigDecimal().setScale(2, RoundingMode.FLOOR).toDouble()