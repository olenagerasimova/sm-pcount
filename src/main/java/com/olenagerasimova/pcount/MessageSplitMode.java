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

/**
 * Long messages split mode.
 * @since 1.0
 */
public enum MessageSplitMode implements Counter {
    /**
     * User Data Header Indicator 8 mode.
     */
    UDHI_8(67, 153),

    /**
     * User Data Header Indicator 16 mode.
     */
    UDHI_16(66, 152),

    /**
     * Tag length value.
     */
    TLV(70, 160),

    /**
     * Payload.
     */
    PAYLOAD(-1, -1) {
        @Override
        public int partsIn(final String message) {
            return 1;
        }
    };

    /**
     * Guarantied unicode one part length.
     */
    private static final int UNICODE_PART_LEN = 70;

    /**
     * Guarantied gsm one part length.
     */
    private static final int GSM_PART_LEN = 160;

    /**
     * Unicode length.
     */
    private final int unicode;

    /**
     * GsmValidator length.
     */
    private final int gsm;

    /**
     * Ctor.
     * @param unicode Unicode length
     * @param gsm GsmValidator length
     */
    MessageSplitMode(final int unicode, final int gsm) {
        this.unicode = unicode;
        this.gsm = gsm;
    }

    /**
     * Parses string into enum item.
     * @param name String representation of message split mode
     * @return This enum item
     */
    @SuppressWarnings("PMD.ProhibitPublicStaticMethods")
    public static MessageSplitMode parse(final String name) {
        for (final MessageSplitMode item : values()) {
            if (item.name().equalsIgnoreCase(name)
                || item.name().replace("_", "-").equalsIgnoreCase(name)) {
                return item;
            }
        }
        throw new IllegalArgumentException(
            String.format("Unknown message preview mode type: %s", name)
        );
    }

    @Override
    public int partsIn(final String message) {
        final int length = message.length();
        final int res;
        if (length == 0) {
            res = 1;
        } else {
            final boolean isgsm = new GsmValidator(message).isGsm();
            if (isgsm && length <= MessageSplitMode.GSM_PART_LEN
                || !isgsm && length <= MessageSplitMode.UNICODE_PART_LEN) {
                res = 1;
            } else if (isgsm) {
                res = length / this.gsm
                    + Math.min(length % this.gsm, 1);
            } else {
                res = length / this.unicode
                    + Math.min(length % this.unicode, 1);
            }
        }
        return res;
    }
}
