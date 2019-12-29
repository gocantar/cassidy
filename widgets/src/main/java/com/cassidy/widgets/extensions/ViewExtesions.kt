package com.cassidy.widgets.extensions

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import androidx.core.content.contentValuesOf

fun View.color(@ColorRes id: Int) = ContextCompat.getColor(context, id)

fun View.dimension(@DimenRes id: Int) = context.resources.getDimension(id)