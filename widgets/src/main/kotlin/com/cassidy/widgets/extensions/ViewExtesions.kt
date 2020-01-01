package com.cassidy.widgets.extensions

import android.view.View
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat

fun View.color(@ColorRes id: Int): Int {
    return ContextCompat.getColor(context, id)
}

fun View.color(@ArrayRes array: Int, position: Int): Int {
    return resources.getIntArray(array)[position]
}

fun View.dimension(@DimenRes id: Int): Float {
    return resources.getDimension(id)
}