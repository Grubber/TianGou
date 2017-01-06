package com.github.grubber.tiangou.ui;

import android.os.Bundle;

import com.github.grubber.tiangou.ui.base.BaseActivity;
import com.github.grubber.tiangou.ui.top.TopActivity;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

/**
 * Created by grubber on 2017/1/6.
 */
public class HomeActivity extends BaseActivity {
    @Inject
    Bus _bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _bus.register(this);
        _bus.post(new OnStartTopActivityEvent());
    }

    @Override
    public void injectMembers() {
        getComponent().inject(this);
    }

    @Subscribe
    public void onStartTopActivity(OnStartTopActivityEvent event) {
        TopActivity.start(this);
        finish();
    }

    @Override
    protected void onDestroy() {
        _bus.unregister(this);
        super.onDestroy();
    }

    static class OnStartTopActivityEvent {
    }
}
