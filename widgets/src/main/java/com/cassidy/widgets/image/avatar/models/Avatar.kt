package com.cassidy.widgets.image.avatar.models

data class Avatar(
    val first: String? = null,
    val second: String? = null,
    val style: Style? = null
) {
    enum class Style { NONE, ONE_INITIAL, TWO_INITIALS, IMAGE }
    enum class Behaviour { FIXED, RAINBOW }
}