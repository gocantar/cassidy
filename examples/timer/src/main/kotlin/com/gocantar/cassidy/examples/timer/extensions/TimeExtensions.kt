package com.gocantar.cassidy.examples.timer.extensions

import com.gocantar.cassidy.examples.timer.models.DisplayTime
import com.gocantar.cassidy.examples.timer.models.Time

fun Time.toDisplay(): DisplayTime {
    val pattern = "%02d"
    val hours = pattern.format(hours)
    val minutes = pattern.format(minutes)
    val seconds = pattern.format(seconds)
    return DisplayTime(hours, minutes, seconds)
}