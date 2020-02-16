package com.gocantar.cassidy.examples.timer

import com.gocantar.cassidy.core.domain.usecase.UseCase
import com.gocantar.cassidy.examples.timer.extensions.toTime
import com.gocantar.cassidy.examples.timer.models.Time
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author Gonzalo Cantarero Pérez, Jan 2020
 */

class TimerUseCase: UseCase<Time, Flow<Time>>() {
    override fun backgroundTask(params: Time?): Flow<Time> {
        var millis = params?.toMillis() ?: return flow { emit(Time()) }
        return flow {
            while(millis >= 0) {
                emit(millis.toTime())
                delay(1_000)
                millis -= 1_000
            }
        }
    }
}