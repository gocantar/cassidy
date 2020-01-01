package com.cassidy.widgets.text

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.LifecycleOwner
import com.cassidy.widgets.base.LifecycleView
import com.cassidy.widgets.exceptions.LifecycleOwnerNotFoundException
import com.gocantar.cassidy.tools.extensions.runIf

/**
 * @author Gonzalo Cantarero Pérez
 */

abstract class CustomTextView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatTextView(context, attrs, defStyleAttr), LifecycleView {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        runIf(isInEditMode.not()) {
            val owner = context as? LifecycleOwner ?: throw LifecycleOwnerNotFoundException()
            onLifecycleOwnerAttached(owner)
        }
    }
}