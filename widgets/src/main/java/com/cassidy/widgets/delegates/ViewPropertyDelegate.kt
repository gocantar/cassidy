package com.cassidy.widgets.delegates

import android.view.View
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author Gonzalo Cantarero Pérez
 */

class ViewPropertyDelegate<T>(private var value: T): ReadWriteProperty<View, T> {

    override fun getValue(thisRef: View, property: KProperty<*>): T {
        return value
    }

    override fun setValue(thisRef: View, property: KProperty<*>, value: T) {
        this.value = value
        thisRef.invalidate()
    }
}

fun <T> View.property(value: T): ViewPropertyDelegate<T> {
    return ViewPropertyDelegate(value)
}