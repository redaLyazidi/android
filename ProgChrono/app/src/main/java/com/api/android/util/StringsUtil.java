package com.api.android.util;

/**
 * Created by reda on 10/12/14.
 */
public final class StringsUtil {

    private StringsUtil() {
    }

    public static boolean isReallyNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
