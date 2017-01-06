package com.github.grubber.tiangou.core.di;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;

import com.github.grubber.tiangou.TGApplication;
import com.github.grubber.tiangou.core.di.qualifier.ApplicationScope;
import com.github.grubber.tiangou.core.di.qualifier.ClientVersionCode;
import com.github.grubber.tiangou.core.di.qualifier.ClientVersionName;
import com.github.grubber.tiangou.core.di.qualifier.ForApplication;
import com.github.grubber.tiangou.util.Preconditions;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

/**
 * Created by grubber on 2017/1/6.
 */
@Module
public class AndroidModule {
    private final Context applicationContext;

    public AndroidModule(TGApplication context) {
        this.applicationContext = Preconditions.checkNotNull(context, "context == null.");
    }

    @ApplicationScope
    @Provides
    @ForApplication
    Context provideApplicationContext() {
        return applicationContext;
    }

    @ApplicationScope
    @Provides
    @ClientVersionCode
    int provideVersionCode(@ForApplication Context context) {
        return getVersionCode(context);
    }

    @ApplicationScope
    @Provides
    @ClientVersionName
    String provideVersionName(@ForApplication Context context) {
        return getVersionName(context);
    }

    @ApplicationScope
    @Provides
    AssetManager provideAssetManager(@ForApplication Context context) {
        return context.getAssets();
    }

    private static int getVersionCode(Context context) {
        PackageInfo pi = getPackageInfo(context);
        if (pi != null) {
            return pi.versionCode;
        }
        return 0;
    }

    private static String getVersionName(Context context) {
        PackageInfo pi = getPackageInfo(context);
        if (pi != null) {
            return pi.versionName;
        } else {
            return "";
        }
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e, "get package manager failed");
        }
        return pi;
    }
}
