package com.cassidy.widgets.base

import androidx.lifecycle.LifecycleOwner

/**
 * @author Gonzalo Cantarero Pérez
 */

interface LifecycleView {
    fun onLifecycleOwnerAttached(lifecycleOwner: LifecycleOwner)
}