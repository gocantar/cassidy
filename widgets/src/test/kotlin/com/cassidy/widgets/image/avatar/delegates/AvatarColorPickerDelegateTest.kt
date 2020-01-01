package com.cassidy.widgets.image.avatar.delegates

import com.cassidy.widgets.image.avatar.models.Avatar
import com.gocantar.cassidy.test.UnitTest
import com.gocantar.cassidy.test.extensions.assertThat
import com.gocantar.cassidy.test.extensions.equal
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Avatar Color Picker Delegate")
class AvatarColorPickerDelegateTest : UnitTest {

    private val delegate: AvatarColorPickerDelegate = AvatarColorPickerDelegate()

    @Test
    @DisplayName("Given fixed behaviour should return [-1]")
    fun givenFixedBehaviour_shouldReturnANumberLessThan0() {
        val avatar = Avatar("first", "second", Avatar.Style.TWO_INITIALS)
        val color = delegate.pick(avatar, Avatar.Behaviour.FIXED)
        color equal -1
    }

    @Test
    @DisplayName("Given rainbow behaviour when style it's image should return [-1]")
    fun givenRainbowBehaviour_whenStyleItsImage_shouldReturnANumberLessThan0() {
        val avatar = Avatar("first", "second", Avatar.Style.IMAGE)
        val color = delegate.pick(avatar, Avatar.Behaviour.RAINBOW)
        color equal -1
    }

    @Test
    @DisplayName("Given rainbow behaviour when style it's with initials should return [0,17]")
    fun givenRainbowBehaviour_whenStyleItsWithInitials_shouldReturnANumberBetween0_17() {
        val avatar = Avatar("first", "second", Avatar.Style.TWO_INITIALS)
        val color = delegate.pick(avatar, Avatar.Behaviour.RAINBOW)
        color.assertThat {
            val isUpperThan0 = this >= 0
            val isLowerThan17 = this <= 17
            isUpperThan0 && isLowerThan17
        }
    }

    @Test
    @DisplayName("Given two equals avatar should return the same color")
    fun givenTwoAvatars_whenHaveSameLabelValues_shouldReturnTheSameColor() {
        val avatar1 = Avatar("first", "second", Avatar.Style.ONE_INITIAL)
        val avatar2 = Avatar("first", "second", Avatar.Style.TWO_INITIALS)
        val color1 = delegate.pick(avatar1, Avatar.Behaviour.RAINBOW)
        val color2 = delegate.pick(avatar2, Avatar.Behaviour.RAINBOW)
        color1 equal color2
    }
}