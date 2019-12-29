package com.cassidy.widgets.image.avatar.formatter

import com.cassidy.widgets.image.avatar.models.Avatar

internal class AvatarFormatter {

    private val EMPTY_STRING = ""

    internal fun format(avatar: Avatar) = when (avatar.style ?: Avatar.Style.DEFAULT) {
        Avatar.Style.DEFAULT -> EMPTY_STRING
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
        return this?.firstOrNull()?.toString() ?: ""
    }
}