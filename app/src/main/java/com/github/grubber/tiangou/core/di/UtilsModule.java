package com.github.grubber.tiangou.core.di;

import android.content.Context;

import com.github.grubber.tiangou.core.di.qualifier.ApplicationScope;
import com.github.grubber.tiangou.core.di.qualifier.ForApplication;
import com.github.grubber.tiangou.util.ToastHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by grubber on 2017/1/6.
 */
@Module
public class UtilsModule {
    @Provides
    @ApplicationScope
    ToastHelper provideToastHelper(@ForApplication Context context) {
        return new ToastHelper(context);
    }
}
