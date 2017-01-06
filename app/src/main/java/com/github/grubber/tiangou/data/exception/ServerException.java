package com.github.grubber.tiangou.data.exception;

/**
 * Created by grubber on 2017/1/6.
 */
public class ServerException extends ApiException {
    private ApiError _apiError;

    public ApiError getApiError() {
        return _apiError;
    }

    public ServerException(String detailMessage, Throwable throwable, ApiError apiError) {
        super(detailMessage, throwable);
        _apiError = apiError;
    }
}
