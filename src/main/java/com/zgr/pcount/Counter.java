/*
 * Copyright (c) 2018 Zagruzka
 */
package com.zgr.pcount;

import javax.validation.constraints.NotNull;

/**
 * Message parts counter.
 * @since 1.0
 */
public interface Counter {

    /**
     * Counts parts of the message.
     * @param message Text message
     * @return How many parts is in this message
     */
    int partsIn(@NotNull String message);
}
