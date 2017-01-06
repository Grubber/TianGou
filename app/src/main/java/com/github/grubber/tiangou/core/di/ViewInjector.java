package com.github.grubber.tiangou.core.di;

import com.github.grubber.tiangou.ui.HomeActivity;
import com.github.grubber.tiangou.ui.top.TopActivity;

/**
 * Created by grubber on 2017/1/6.
 */
public interface ViewInjector {
    void inject(HomeActivity homeActivity);

    void inject(TopActivity topActivity);
}
