package com.api.android.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by reda on 10/19/14.
 */
public final class CollectionArrayUtil {

    private CollectionArrayUtil() {
    }

    public static <T> boolean isNullOrEmptyArray(long[] array) {
        return array == null || array.length == 0;
    }

    public static <T> boolean isNullOrEmptyArray(int[] array) {
        return array == null || array.length == 0;
    }

    public static <T> boolean isNullOrEmptyArray(T[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNullOrEmpty(Collection<?> collection) { return collection == null || collection.isEmpty();}

    public static List<Long> asListLong(long[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        final int arrayLength = array.length;
        List<Long> list = new ArrayList<>(arrayLength);
        for (int i = 0; i < arrayLength; i++) {
            list.add(i,array[i]);
        }
        return list;
    }

    public static <T extends Number> String toStringArray(T[] array) {
        if (isNullOrEmptyArray(array)) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for(T current : array) {
            if (current != null) {
                builder.append(current.intValue()).append(',');
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(" ]");
        return builder.toString();
    }

    public static String toStringArray(long[] array) {
        if (isNullOrEmptyArray(array)) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for(long current : array) {
            builder.append(current).append(',');
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(']');
        return builder.toString();
    }

    public static boolean containsLong(long[] array, long value) {
        if (isNullOrEmptyArray(array)) {
            return false;
        }
        for (long current : array) {
            if (value == current) {
                return true;
            }
        }
        return false;
    }
}
