package com.github.grubber.tiangou.data.api.service;

import com.github.grubber.tiangou.data.api.model.Top;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by grubber on 2017/1/6.
 */
public interface TopService {
    @GET("top/list")
    Observable<Top.Result> list();
}
