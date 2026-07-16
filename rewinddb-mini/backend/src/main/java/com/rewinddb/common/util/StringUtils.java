package com.rewinddb.common.util;

public final class StringUtils {
    private StringUtils() {
    }

    public static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
