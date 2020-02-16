package com.gocantar.cassidy.core.presentation

import androidx.lifecycle.LiveData

/**
 * @author Gonzalo Cantarero Pérez, Jan 2020
 */

abstract class ViewModel<State> : androidx.lifecycle.ViewModel() {

    /**
     * @state Live data that represent View's state.
     */
    abstract val state: LiveData<State>
}