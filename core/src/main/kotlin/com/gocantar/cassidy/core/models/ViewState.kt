package com.gocantar.cassidy.core.models

/**
 * @author Gonzalo Cantarero Pérez, Jan 2020
 */

sealed class ViewState {
    data class Default<T>(val data: T) : ViewState()
    data class Error(val message: String? = null) : ViewState()
    object Loading : ViewState()
}

