package com.github.grubber.tiangou.util;

/**
 * Created by grubber on 2017/1/6.
 */
public class Preconditions {
    public static <T> T checkNotNull(T t) {
        if (t == null)
            throw new NullPointerException();
        return t;
    }

    public static <T> T checkNotNull(T t, Object value) {
        if (t == null)
            throw new NullPointerException(String.valueOf(value));
        return t;
    }
}
