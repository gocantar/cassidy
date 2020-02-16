package com.gocantar.cassidy.examples.timer.extensions

import com.gocantar.cassidy.examples.timer.models.Time
import java.util.concurrent.*

fun Long.toTime(): Time {
    val hours = TimeUnit.MILLISECONDS.toHours(this)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this)
    return Time(
        hours = hours,
        minutes = minutes - TimeUnit.HOURS.toMinutes(hours),
        seconds = seconds - TimeUnit.MINUTES.toSeconds(minutes)
    )
}