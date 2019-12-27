package com.gocantar.cassidy.tools.extensions

/**
 * @author Gonzalo Cantarero Pérez, Mar 2019
 */

inline fun runIf(condition: Boolean, block: () -> Unit) {
    if (condition) block()
}