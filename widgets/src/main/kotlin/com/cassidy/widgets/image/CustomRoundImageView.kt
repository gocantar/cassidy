package com.cassidy.widgets.image

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import com.cassidy.widgets.base.LifecycleView
import com.cassidy.widgets.exceptions.LifecycleOwnerNotFoundException
import com.gocantar.cassidy.tools.extensions.runIf

abstract class CustomRoundImageView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    RoundImageView(context, attrs, defStyleAttr), LifecycleView {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        runIf(isInEditMode.not()) {
            val owner = context as? LifecycleOwner ?: throw LifecycleOwnerNotFoundException()
            onLifecycleOwnerAttached(owner)
        }
    }
}