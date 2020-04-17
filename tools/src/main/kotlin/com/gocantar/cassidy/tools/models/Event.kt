package com.gocantar.cassidy.tools.models


/**
 * @author Gonzalo Cantarero Pérez, Abr 2020
 */

class Event<out T>(private val content: T) {

    var wasHandled = false
        private set

    /**
     * Get the content when hasn't been provided before.
     * @return the content or null if event hasn't been handled.
     */
    fun getOrNull(): T? = content.takeIf { wasHandled.not() }.also { wasHandled = true }

    /**
     * Get the content, even if it's already been handled.
     * @return the content
     */
    fun peek(): T = content
}