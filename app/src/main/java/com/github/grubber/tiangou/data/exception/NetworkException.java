package com.github.grubber.tiangou.data.exception;

/**
 * Created by grubber on 2017/1/6.
 */
public class NetworkException extends ApiException {
    public NetworkException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
