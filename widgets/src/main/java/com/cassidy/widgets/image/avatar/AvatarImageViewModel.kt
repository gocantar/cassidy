package com.cassidy.widgets.image.avatar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.cassidy.widgets.image.avatar.delegates.AvatarColorPickerDelegate
import com.cassidy.widgets.image.avatar.formatters.AvatarFormatter
import com.cassidy.widgets.image.avatar.models.Avatar

internal class AvatarImageViewModel(
    private val formatter: AvatarFormatter = AvatarFormatter(),
    private val colorPicker: AvatarColorPickerDelegate = AvatarColorPickerDelegate()
) {

    private val state: MutableLiveData<Avatar> = MutableLiveData()
    internal val avatar: LiveData<Pair<String?, Int>> = state.map { transform(it) }

    private var backgroundBehaviour: Avatar.Behaviour = Avatar.Behaviour.FIXED

    private val style: Avatar.Style get() = state.value?.style ?: Avatar.Style.ONE_INITIAL

    internal fun configure(avatarStyle: Int, behaviour: Int) {
        backgroundBehaviour = Avatar.Behaviour.values()[behaviour]
        state.value = Avatar(style = Avatar.Style.values()[avatarStyle])
    }

    internal fun setAvatar(value: Avatar) {
        state.value = Avatar(value.first, value.second, value.style ?: style)
    }

    private fun transform(avatar: Avatar): Pair<String?, Int> {
        val color = colorPicker.pick(avatar, backgroundBehaviour)
        val initials = format(avatar)
        return Pair(initials, color)
    }

    private fun format(avatar: Avatar) = when (avatar.style ?: Avatar.Style.ONE_INITIAL) {
        Avatar.Style.ONE_INITIAL,
        Avatar.Style.TWO_INITIALS -> formatter.format(avatar)
        else -> null
    }
}