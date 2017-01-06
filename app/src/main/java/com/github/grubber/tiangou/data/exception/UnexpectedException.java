package com.github.grubber.tiangou.data.exception;

/**
 * Created by grubber on 2017/1/6.
 */
public class UnexpectedException extends ApiException {
    public UnexpectedException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
