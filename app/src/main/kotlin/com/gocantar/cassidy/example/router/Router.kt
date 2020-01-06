package com.gocantar.cassidy.example.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import java.util.Locale

object Router {
    fun navigateTo(id: String, context: Context) {
        val activity = context as Activity
        activity.startActivity(Intent(Intent.ACTION_VIEW, id.toUri()))
    }

    private fun String.toUri(): Uri {
        val destination = toLowerCase(Locale.getDefault())
        return Uri.Builder()
            .path("examples/$destination")
            .authority("cassidy.com")
            .scheme("app")
            .build()
    }
}