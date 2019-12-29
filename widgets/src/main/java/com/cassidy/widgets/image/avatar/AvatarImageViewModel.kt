package com.cassidy.widgets.image.avatar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.cassidy.widgets.image.avatar.formatter.AvatarFormatter
import com.cassidy.widgets.image.avatar.models.Avatar


internal class AvatarImageViewModel(private val formatter: AvatarFormatter = AvatarFormatter()) {

    private val avatar: MutableLiveData<Avatar> = MutableLiveData()

    internal val text: LiveData<String?> = avatar.map { transform(it) }

    private val style: Avatar.Style
        get() = avatar.value?.style ?: Avatar.Style.DEFAULT

    internal fun configure(avatarStyle: Int) {
        val style = Avatar.Style.values()[avatarStyle]
        avatar.value = Avatar(style = style)
    }

    internal fun setAvatar(value: Avatar) {
        avatar.value = Avatar(value.first, value.second, value.style ?: style)
    }

    private fun transform(avatar: Avatar) = when (avatar.style ?: Avatar.Style.DEFAULT) {
        Avatar.Style.DEFAULT,
        Avatar.Style.ONE_INITIAL,
        Avatar.Style.TWO_INITIALS -> formatter.format(avatar)
        else -> null
    }
}