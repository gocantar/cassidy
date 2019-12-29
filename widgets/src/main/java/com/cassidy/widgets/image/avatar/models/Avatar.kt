package com.cassidy.widgets.image.avatar.models

data class Avatar(
   val first: String? = null,
   val second: String? = null,
   val style: Style? = null
) {
    enum class Style { DEFAULT, ONE_INITIAL, TWO_INITIALS, IMAGE }
}