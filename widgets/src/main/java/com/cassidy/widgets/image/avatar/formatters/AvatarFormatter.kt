package com.cassidy.widgets.image.avatar.formatters

import com.cassidy.widgets.image.avatar.models.Avatar
import java.util.Locale

internal class AvatarFormatter {

    private val EMPTY_STRING = ""

    internal fun format(avatar: Avatar) = when (avatar.style ?: Avatar.Style.ONE_INITIAL) {
        Avatar.Style.ONE_INITIAL -> avatar.oneInitialFormat()
        Avatar.Style.TWO_INITIALS -> avatar.twoInitialsFormat()
        Avatar.Style.IMAGE -> null
    }

    private fun Avatar.oneInitialFormat(): String {
        return this.first.extractInitial()
    }

    private fun Avatar.twoInitialsFormat(): String {
        return "${this.first.extractInitial()}${this.second.extractInitial()}"
    }

    private fun String?.extractInitial(): String {
        return this?.firstOrNull()?.toString()?.toUpperCase(Locale.getDefault()) ?: EMPTY_STRING
    }
}