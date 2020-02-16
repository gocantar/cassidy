package com.gocantar.cassidy.examples.timer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.gocantar.cassidy.core.models.ViewState
import com.gocantar.cassidy.core.presentation.ViewModel
import com.gocantar.cassidy.examples.timer.extensions.toDisplay
import com.gocantar.cassidy.examples.timer.models.DisplayTime
import com.gocantar.cassidy.examples.timer.models.Time
import kotlinx.coroutines.flow.map

/**
 * @author Gonzalo Cantarero Pérez, Jan 2020
 */

class TimerViewModel(private val useCase: TimerUseCase = TimerUseCase()) : ViewModel<ViewState>() {

    private val TIME = Time(minutes = 1L)

    override val state: LiveData<ViewState> = MutableLiveData()

    private val time: MutableLiveData<Time> = MutableLiveData()
    val displayTime: LiveData<DisplayTime> = time.switchMap {
        liveData {
            emitSource(executeTimerUseCase())
        }
    }

    fun initializeTimer() {
        time.value = TIME
    }

    private suspend fun executeTimerUseCase() : LiveData<DisplayTime> {
       return useCase(TIME)
           .map { it.toDisplay() }
           .asLiveData()
    }
}