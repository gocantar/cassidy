package com.gocantar.cassidy.examples.timer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gocantar.cassidy.examples.timer.models.DisplayTime
import com.gocantar.cassidy.examples.timer.models.Time
import kotlinx.android.synthetic.main.activity_timer.*

/**
 * @author Gonzalo Cantarero Pérez, Jan 2020
 */

class TimerActivity : AppCompatActivity() {

    private val viewModel: TimerViewModel by lazy {
        ViewModelProvider(this)[TimerViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        configureView()
        viewModel.displayTime.observe(this, Observer { time ->
            timerLabel.text = formatTime(time)
        })
    }

    private fun configureView() {
        timerLabel.text = formatTime(DisplayTime())
        startButton.setOnClickListener {
            viewModel.initializeTimer()
        }
    }

    private fun formatTime(time: DisplayTime): String {
        return String.format("%s:%s:%s", time.hours, time.minutes, time.seconds)
    }
}
