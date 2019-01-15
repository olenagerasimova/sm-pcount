/*
 * Copyright (c) 2018 Zagruzka
 */
package com.zgr.pcount;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link Gsm}.
 * @since 1.0
 * @checkstyle AbbreviationAsWordInNameCheck (500 lines)
 */
final class GsmTest {

    @Test
    void returnsTrueIfMessageIsGSM() {
        MatcherAssert.assertThat(
            Gsm.isGsm("abc"),
            new IsEqual<>(true)
        );
    }

    @Test
    void returnsFalseIfMessageContainsRussian() {
        MatcherAssert.assertThat(
            Gsm.isGsm("абв"),
            new IsEqual<>(false)
        );
    }

    @Test
    void returnsTrueIfMessageContainsGSMExtended() {
        MatcherAssert.assertThat(
            Gsm.isGsm("a~bc"),
            new IsEqual<>(true)
        );
    }
}
