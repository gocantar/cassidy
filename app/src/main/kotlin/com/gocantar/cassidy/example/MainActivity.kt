package com.gocantar.cassidy.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gocantar.cassidy.app.R
import com.gocantar.cassidy.example.router.Router
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val items = listOf("Timer", "Teams")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureView()
    }

    private fun configureView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ItemsAdapter(items) {
            Router.navigateTo(it, this)
        }
    }
}
