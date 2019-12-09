package com.cassidy.widgets.extensions

import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun View.color(@ColorRes id: Int) = ContextCompat.getColor(context, id)