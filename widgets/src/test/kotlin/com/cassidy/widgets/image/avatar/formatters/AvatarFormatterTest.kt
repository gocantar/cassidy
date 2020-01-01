package com.cassidy.widgets.image.avatar.formatters

import com.cassidy.widgets.image.avatar.models.Avatar
import com.gocantar.cassidy.test.UnitTest
import com.gocantar.cassidy.test.extensions.equal
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

/**
 * @author Gonzalo Cantarero Pérez, Dic 2019
 */

@DisplayName("Avatar formatter")
class AvatarFormatterTest : UnitTest {

    @DisplayName("Get initials")
    @ParameterizedTest(name = "Should be format to {1}")
    @MethodSource("parameterizedArguments")
    fun shouldFormatPositiveAmount(avatar: Avatar, avatarFormatted: String?) {
        val formatter = AvatarFormatter()
        val result = formatter.format(avatar)
        result equal avatarFormatted
    }

    companion object {
        @JvmStatic
        fun parameterizedArguments(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(Avatar("First", "Second", Avatar.Style.IMAGE), null),
                Arguments.of(Avatar("First", "Second", Avatar.Style.ONE_INITIAL), "F"),
                Arguments.of(Avatar("First", null, Avatar.Style.ONE_INITIAL), "F"),
                Arguments.of(Avatar(null, "Second", Avatar.Style.ONE_INITIAL), ""),
                Arguments.of(Avatar("first", "second", Avatar.Style.ONE_INITIAL), "F"),
                Arguments.of(Avatar("First", "Second", Avatar.Style.TWO_INITIALS), "FS"),
                Arguments.of(Avatar("First", null, Avatar.Style.TWO_INITIALS), "F"),
                Arguments.of(Avatar(null, "Second", Avatar.Style.TWO_INITIALS), "S"),
                Arguments.of(Avatar("first", "second", Avatar.Style.TWO_INITIALS), "FS")
            )
        }
    }
}