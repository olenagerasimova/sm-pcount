/*
 * MIT License
 *
 * Copyright (c) 2020 olenagerasimova
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.olenagerasimova.pcount;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link MessageSplitMode}.
 * @since 1.0
 * @checkstyle AbbreviationAsWordInNameCheck (500 lines)
 * @checkstyle MethodNameCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
final class MessageSplitModeTest {

    /**
     * Unicode message.
     * @checkstyle LineLengthCheck (2 lines)
     */
    private static final String UNICODE_MESSAGE = "У вас 8585948б! 1б=1руб! Меняйте до 50% на золото/серебро! Меняйте до 70% на медь! Меняйте до 20% камни/побрекушки/безделушки/ракушки/каку!";

    /**
     * GSM message.
     * @checkstyle LineLengthCheck (2 lines)
     */
    private static final String GSM_MESSAGE = "Avoid composite names of variables, like timeOfDay, firstItem, or httpRequest. I mean with both - class variables and in-method ones. A variable name should be long enough to avoid ambiguity in its scope of visibility, but not too long if possible. A name should be a noun in singular or plural form, or a";

    @Test
    void udhi8ModeReturnsTwoPartsForGSMMessage() {
        MatcherAssert.assertThat(
            MessageSplitMode.UDHI_8.partsIn(MessageSplitModeTest.GSM_MESSAGE),
            new IsEqual<>(2)
        );
    }

    @Test
    void tlvModeReturnsTwoPartsForGSMMessage() {
        MatcherAssert.assertThat(
            MessageSplitMode.TLV.partsIn(MessageSplitModeTest.GSM_MESSAGE),
            new IsEqual<>(2)
        );
    }

    @Test
    void udhi16ModeReturnsThreePartsForGSMMessage() {
        MatcherAssert.assertThat(
            MessageSplitMode.UDHI_16.partsIn(MessageSplitModeTest.GSM_MESSAGE),
            new IsEqual<>(3)
        );
    }

    @Test
    void udhi8ModeReturnsThreePartForUnicodeMessage() {
        MatcherAssert.assertThat(
            MessageSplitMode.UDHI_8
                .partsIn(MessageSplitModeTest.UNICODE_MESSAGE),
            new IsEqual<>(3)
        );
    }

    @Test
    void tlvModeReturnsTwoPartsForUnicodeMessage() {
        MatcherAssert.assertThat(
            MessageSplitMode.TLV.partsIn(MessageSplitModeTest.UNICODE_MESSAGE),
            new IsEqual<>(2)
        );
    }

    @Test
    void udhi16ModeReturnsThreePartsForUnicodeMessage() {
        MatcherAssert.assertThat(
            MessageSplitMode.UDHI_16
                .partsIn(MessageSplitModeTest.UNICODE_MESSAGE),
            new IsEqual<>(3)
        );
    }

    @Test
    void returnsOnePartForEmptyMessage() {
        MatcherAssert.assertThat(
            MessageSplitMode.UDHI_8.partsIn(""),
            new IsEqual<>(1)
        );
    }

    @Test
    void returnsOnePartForShortMessage() {
        MatcherAssert.assertThat(
            MessageSplitMode.UDHI_8.partsIn("msg"),
            new IsEqual<>(1)
        );
    }

    @Test
    void returnsOnePartForPayload() {
        MatcherAssert.assertThat(
            MessageSplitMode.PAYLOAD.partsIn("any message"),
            new IsEqual<>(1)
        );
    }

    @Test
    void parsesString() {
        MatcherAssert.assertThat(
            MessageSplitMode.parse("UDHI-16"),
            new IsEqual<>(MessageSplitMode.UDHI_16)
        );
    }
}
