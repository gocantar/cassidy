package com.gocantar.cassidy.examples.timer.models

import java.util.concurrent.*

data class Time(val hours: Long = 0L, val minutes: Long = 0L, val seconds: Long = 0L) {

    fun toMillis(): Long {
        val hoursInMillis = TimeUnit.HOURS.toMillis(hours)
        val minutesInMillis = TimeUnit.MINUTES.toMillis(minutes)
        val secondsInMillis = TimeUnit.SECONDS.toMillis(seconds)
        return hoursInMillis + minutesInMillis + secondsInMillis
    }
}