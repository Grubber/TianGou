package com.github.grubber.tiangou;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.frogermcs.androiddevmetrics.AndroidDevMetrics;
import com.github.grubber.tiangou.core.di.AndroidModule;
import com.github.grubber.tiangou.core.di.ApiModule;
import com.github.grubber.tiangou.core.di.ApplicationComponent;
import com.github.grubber.tiangou.core.di.DaggerApplicationComponent;
import com.github.grubber.tiangou.core.di.DataModule;
import com.github.grubber.tiangou.core.di.HasComponent;
import com.github.grubber.tiangou.core.di.NetworkModule;
import com.github.grubber.tiangou.core.di.UtilsModule;

import timber.log.Timber;

/**
 * Created by grubber on 2017/1/6.
 */
public class TGApplication extends Application implements HasComponent<ApplicationComponent> {
    private ApplicationComponent _component;

    @Override
    public void onCreate() {
        super.onCreate();

        _buildObjectGraph();
        _setupAnalytics();
    }

    private void _setupAnalytics() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            AndroidDevMetrics.initWith(this);
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                            .build());
        }
    }

    private void _buildObjectGraph() {
        _component = DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .dataModule(new DataModule())
                .networkModule(new NetworkModule())
                .apiModule(new ApiModule())
                .utilsModule(new UtilsModule())
                .build();
    }

    @Override
    public ApplicationComponent getComponent() {
        return _component;
    }

    public static TGApplication from(Context context) {
        return (TGApplication) context.getApplicationContext();
    }
}
