package com.cassidy.widgets.image.avatar.delegates

import com.cassidy.widgets.image.avatar.models.Avatar
import kotlin.math.abs

class AvatarColorPickerDelegate {

    private val ARRAY_COLORS_SIZE = 18

    fun pick(avatar: Avatar, behaviour: Avatar.Behaviour) = when (behaviour) {
        Avatar.Behaviour.FIXED -> FIXED
        Avatar.Behaviour.RAINBOW -> pickColor(avatar)
    }

    private fun pickColor(avatar: Avatar) = when (avatar.style ?: Avatar.Style.NONE) {
        Avatar.Style.IMAGE, Avatar.Style.NONE -> FIXED
        Avatar.Style.ONE_INITIAL, Avatar.Style.TWO_INITIALS -> avatar.hash() % ARRAY_COLORS_SIZE
    }

    private fun Avatar.hash(): Int {
        val hash = first.hashCode() + second.hashCode()
        return abs(hash)
    }

    companion object {
        const val FIXED: Int = -1
    }
}