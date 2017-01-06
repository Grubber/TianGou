package com.github.grubber.tiangou.data.exception;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by grubber on 2017/1/6.
 */
public class ApiException extends RuntimeException {
    public ApiException(Throwable throwable) {
        super(throwable);
    }

    public ApiException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public static ApiException create(Throwable throwable, Retrofit retrofit) {
        if (throwable instanceof HttpException) {
            Converter<ResponseBody, ApiError> errorConverter = retrofit
                    .responseBodyConverter(ApiError.class, new Annotation[0]);
            ApiError apiError = null;
            try {
                apiError = errorConverter
                        .convert(((HttpException) throwable).response().errorBody());
            } catch (IOException e) {
                // ignore. apiError is null.
            }
            return new ServerException("Server error", throwable, apiError);
        } else if (throwable instanceof IOException) {
            return new NetworkException("network error", throwable);
        } else {
            return new UnexpectedException("unexpected error", throwable);
        }
    }
}
