package com.github.grubber.tiangou.core;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * Created by grubber on 2017/1/6.
 */
public class EventBus extends Bus {
    private static Bus INSTANCE;
    private final Handler _mainThread = new Handler(Looper.getMainLooper());

    private EventBus() {
    }

    public synchronized static Bus getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EventBus();
        }

        return INSTANCE;
    }

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            _mainThread.post(new Runnable() {
                @Override
                public void run() {
                    EventBus.super.post(event);
                }
            });
        }
    }
}
