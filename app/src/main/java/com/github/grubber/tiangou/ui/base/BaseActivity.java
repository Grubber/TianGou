package com.github.grubber.tiangou.ui.base;

import android.os.Bundle;

import com.github.grubber.tiangou.TGApplication;
import com.github.grubber.tiangou.core.di.ApplicationComponent;
import com.github.grubber.tiangou.core.di.HasComponent;
import com.github.grubber.tiangou.core.di.Injectable;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by grubber on 2017/1/6.
 */
public abstract class BaseActivity extends RxAppCompatActivity
        implements HasComponent<ApplicationComponent>, Injectable {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectMembers();
    }

    @Override
    public ApplicationComponent getComponent() {
        return TGApplication.from(this).getComponent();
    }

    protected <T> Observable<T> bind$(Observable<T> observable) {
        return observable.compose(this.<T>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected <T> Observable<T> bind$(Observable<T> observable, ActivityEvent
            activityEvent) {
        return observable.compose(this.<T>bindUntilEvent(activityEvent))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
