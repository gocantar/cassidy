package com.gocantar.cassidy.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.gocantar.cassidy.app.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        roundImage.setOnClickListener {
            count ++
            Toast.makeText(baseContext, "on click: $count times", Toast.LENGTH_LONG).show()
        }
    }
}
