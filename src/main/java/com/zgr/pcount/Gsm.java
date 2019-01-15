/*
 * Copyright (c) 2018 Zagruzka
 */
package com.zgr.pcount;

import java.nio.charset.Charset;
import javax.validation.constraints.NotNull;

/**
 * Group Special Mobile class can determine whether message is gsm or not.
 * @since 1.0
 */
final class Gsm {

    /**
     * Gsm to unicode table.
     * @checkstyle ConstantNameCheck
     * @checkstyle RegexpMultilineCheck
     */
    @SuppressWarnings("PMD.FieldNamingConventions")
    private static final int[] GSM_2_UNICODE = {

        // At Pd DO Ye e! e' u! i!
        // o! C, LF O/ o/ CR AA aa

        0x0040, 0x00A3, 0x0024, 0x00A5, 0x00E8, 0x00E9, 0x00F9, 0x00EC,
        0x00F2, 0x00C7, 0x000A, 0x00D8, 0x00F8, 0x000D, 0x00C5, 0x00E5,

        // D* _  F* G* L* W* P* Q*
        // S* H* C* EC AE ae ss E'

        0x0394, 0x005F, 0x03A6, 0x0393, 0x039B, 0x03A9, 0x03A0, 0x03A8,
        0x03A3, 0x0398, 0x039E, 0x001B, 0x00C6, 0x00E6, 0x00DF, 0x00C9,

        // SP ! " NS Cu % & '
        // ( ) * + , - . /

        0x0020, 0x0021, 0x0022, 0x0023, 0x00A4, 0x0025, 0x0026, 0x0027,
        0x0028, 0x0029, 0x002A, 0x002B, 0x002C, 0x002D, 0x002E, 0x002F,

        // 0 1 2 3 4 5 6 7
        // 8 9 : ; < = > ?

        0x0030, 0x0031, 0x0032, 0x0033, 0x0034, 0x0035, 0x0036, 0x0037,
        0x0038, 0x0039, 0x003A, 0x003B, 0x003C, 0x003D, 0x003E, 0x003F,

        // !I A B C D E F G
        // H  I J K L M N O

        0x00A1, 0x0041, 0x0042, 0x0043, 0x0044, 0x0045, 0x0046, 0x0047,
        0x0048, 0x0049, 0x004A, 0x004B, 0x004C, 0x004D, 0x004E, 0x004F,

        // P Q R S T U V W
        // X Y Z A: O: N? U: SE

        0x0050, 0x0051, 0x0052, 0x0053, 0x0054, 0x0055, 0x0056, 0x0057,
        0x0058, 0x0059, 0x005A, 0x00C4, 0x00D6, 0x00D1, 0x00DC, 0x00A7,

        // ?I a b c d e f g
        // h i j k l m n o

        0x00BF, 0x0061, 0x0062, 0x0063, 0x0064, 0x0065, 0x0066, 0x0067,
        0x0068, 0x0069, 0x006A, 0x006B, 0x006C, 0x006D, 0x006E, 0x006F,

        // p q r s t u v w
        // x y z a: o: n? u: a!

        0x0070, 0x0071, 0x0072, 0x0073, 0x0074, 0x0075, 0x0076, 0x0077,
        0x0078, 0x0079, 0x007A, 0x00E4, 0x00F6, 0x00F1, 0x00FC, 0x00E0,

    };

    /**
     * GSM extension for utf16.
     */
    private static final int[] GSM_EXT_UTF16 = {
        0x005E, 0x007B, 0x007D, 0x005C, 0x005B, 0x007E, 0x005D, 0x007C, 0x20AC,
    };

    /**
     * GSM extension.
     */
    private static final int[] GSM_EXT_GSM = {
        0x14, 0x28, 0x29, 0x2F, 0x3C, 0x3D, 0x3E, 0x40, 0x65,
    };

    /**
     * Ctor.
     */
    private Gsm() {
    }

    /**
     * Determine where message has only gsm default chars.
     * @param message Message to check
     * @return True if message contains GSM symbols only
     * @checkstyle IllegalTokenCheck (10 lines)
     * @checkstyle MagicNumberCheck (10 lines)
     */
    static boolean isGsm(@NotNull final String message) {
        final byte[] bytes = message.getBytes(Charset.forName("UTF-16BE"));
        int idx = 0;
        boolean res = true;
        while (idx < bytes.length) {
            final int code = ((int) bytes[idx++] & 0xFF) << 8
                | ((int) bytes[idx++] & 0xFF);
            if (Gsm.gsmCode(code) < 0 && Gsm.gsmExtensionCode(code) < 0) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * Returns GSM code for unicode.
     * @param unicode Unicode
     * @return GSM code if exists and -1 if not
     */
    private static int gsmCode(final int unicode) {
        int res = -1;
        for (int cur = 0; cur < Gsm.GSM_2_UNICODE.length; cur = cur + 1) {
            if (unicode == Gsm.GSM_2_UNICODE[cur]) {
                res = cur;
                break;
            }
        }
        return res;
    }

    /**
     * Returns GSM extension code for unicode.
     * @param unicode Unicode
     * @return GSM extension code if exists and -1 if not
     */
    private static int gsmExtensionCode(final int unicode) {
        int res = -1;
        for (int cur = 0; cur < Gsm.GSM_EXT_UTF16.length; cur = cur + 1) {
            if (Gsm.GSM_EXT_UTF16[cur] == unicode) {
                res = Gsm.GSM_EXT_GSM[cur];
                break;
            }
        }
        return res;
    }
}
