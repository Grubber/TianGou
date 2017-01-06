package com.github.grubber.tiangou.core.di;

import android.content.Context;
import android.net.Uri;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.github.grubber.tiangou.BuildConfig;
import com.github.grubber.tiangou.core.di.qualifier.ApplicationScope;
import com.github.grubber.tiangou.core.di.qualifier.ForApplication;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by grubber on 2017/1/6.
 */
@Module
public class NetworkModule {
    private static final long OKCLIENT_DISK_CACHE_SIZE = 20 * 1024 * 1024;
    private static final String OKCLIENT_DISK_CACHE_NAME = "http-cache";

    private static OkHttpClient _createOkHttpClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15000L, TimeUnit.MILLISECONDS)
                .readTimeout(20000L, TimeUnit.MILLISECONDS)
                .writeTimeout(15000L, TimeUnit.MILLISECONDS);

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new StethoInterceptor())
                    .addInterceptor(new HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY))
                    .cache(new Cache(
                            new File(context.getExternalCacheDir(), OKCLIENT_DISK_CACHE_NAME),
                            OKCLIENT_DISK_CACHE_SIZE));
        } else {
            builder.cache(
                    new Cache(new File(context.getCacheDir(), OKCLIENT_DISK_CACHE_NAME),
                            OKCLIENT_DISK_CACHE_SIZE));
        }
        return builder.build();
    }

    @Provides
    @ApplicationScope
    OkHttpClient provideOkHttpClient(@ForApplication Context context) {
        return _createOkHttpClient(context);
    }

    @Provides
    @ApplicationScope
    Picasso providePicasso(@ForApplication Context context, OkHttpClient okHttpClient) {
        OkHttpClient.Builder builder = okHttpClient.newBuilder();
        builder.interceptors().clear();
        builder.addInterceptor(new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.HEADERS));

        Picasso picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(builder.build()))
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri,
                                                  Exception exception) {
                        Timber.e(exception, "### Failed to load image: %s", uri);
                    }
                }).build();

        if (BuildConfig.DEBUG) {
            picasso.setIndicatorsEnabled(true);
        }

        return picasso;
    }
}
