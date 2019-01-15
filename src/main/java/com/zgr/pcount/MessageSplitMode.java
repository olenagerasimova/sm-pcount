/*
 * Copyright (c) 2018 Zagruzka
 */
package com.zgr.pcount;

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
     * Gsm length.
     */
    private final int gsm;

    /**
     * Ctor.
     * @param unicode Unicode length
     * @param gsm Gsm length
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
            final boolean isgsm = Gsm.isGsm(message);
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
