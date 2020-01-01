package com.cassidy.widgets.image.avatar

import androidx.lifecycle.Observer
import com.cassidy.widgets.image.avatar.delegates.AvatarColorPickerDelegate
import com.cassidy.widgets.image.avatar.formatters.AvatarFormatter
import com.cassidy.widgets.image.avatar.models.Avatar
import com.gocantar.cassidy.test.UnitTest
import com.gocantar.cassidy.test.extensions.mock
import com.gocantar.cassidy.test.rules.InstantTaskExecutorExtension
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.excludeRecords
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@DisplayName("Amount Text View")
@ExtendWith(InstantTaskExecutorExtension::class)
class AvatarImageViewModelTest : UnitTest {

    private val observer: Observer<Pair<String?, Int>> = mock()
    private val formatter: AvatarFormatter = mock()
    private val colorPicker: AvatarColorPickerDelegate = mock()

    private val viewModel: AvatarImageViewModel = AvatarImageViewModel(formatter, colorPicker)

    @Test
    @DisplayName("Given initial configuration should be update label")
    fun givenInitialConfiguration_thenUpdateLabelWithEmptyString() {
        every { colorPicker.pick(any(), Avatar.Behaviour.FIXED) } returns -1
        every { formatter.format(Avatar("", "", Avatar.Style.ONE_INITIAL)) } returns ""
        viewModel.avatar.observeForever(observer)
        viewModel.configure(0, 0)
        verify { observer.onChanged(Pair("", -1)) }
        confirmVerified(observer)
    }

    @Test
    @DisplayName("Given new avatar when style is not update should update with previous style")
    fun givenNewAvatar_thenUpdateLabel() {
        warmUp()
        viewModel.avatar.observeForever(observer)
        viewModel.setAvatar(Avatar("First", "Second"))
        verify { observer.onChanged(Pair("FS", -1)) }
        confirmVerified(observer)
    }

    @Test
    @DisplayName("When change avatar with new style should be update initials with style applied")
    fun givenNewAvatarAndStyle_thenUpdateLabel() {
        warmUp()
        viewModel.avatar.observeForever(observer)
        viewModel.setAvatar(Avatar("First", "Second", Avatar.Style.ONE_INITIAL))
        verify { observer.onChanged(Pair("F", -1)) }
        confirmVerified(observer)
    }

    private fun warmUp() {
        every { colorPicker.pick(any(), Avatar.Behaviour.FIXED) } returns -1
        every { formatter.format(Avatar("", "", Avatar.Style.ONE_INITIAL)) } returns ""
        every { formatter.format(Avatar("", "", Avatar.Style.TWO_INITIALS)) } returns ""
        every { formatter.format(Avatar("First", "Second", Avatar.Style.ONE_INITIAL)) } returns "F"
        every { formatter.format(Avatar("First", "Second", Avatar.Style.TWO_INITIALS)) } returns "FS"
        excludeRecords { observer.onChanged(Pair("", -1)) }
        viewModel.configure(1, 0)
    }
}