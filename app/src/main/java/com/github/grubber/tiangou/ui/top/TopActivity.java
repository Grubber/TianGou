package com.github.grubber.tiangou.ui.top;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.github.grubber.tiangou.R;
import com.github.grubber.tiangou.core.di.qualifier.ClientVersionCode;
import com.github.grubber.tiangou.core.di.qualifier.ClientVersionName;
import com.github.grubber.tiangou.data.exception.ApiException;
import com.github.grubber.tiangou.data.api.model.Top;
import com.github.grubber.tiangou.data.api.service.TopService;
import com.github.grubber.tiangou.ui.base.BaseActivity;
import com.github.grubber.tiangou.util.ToastHelper;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import rx.Observer;
import timber.log.Timber;

/**
 * Created by grubber on 2017/1/6.
 */
public class TopActivity extends BaseActivity {
    @Inject
    TopService _topService;
    @Inject
    Picasso _picasso;
    @Inject
    @ClientVersionCode
    int _versionCode;
    @Inject
    @ClientVersionName
    String _versionName;
    @Inject
    ToastHelper _toastHelper;
    @Inject
    Retrofit _retrofit;

    @Bind(R.id.common_list)
    ListView _listView;

    private TopAdapter _topAdapter;

    public static void start(Context context) {
        context.startActivity(new Intent(context, TopActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        ButterKnife.bind(this);

        _topAdapter = new TopAdapter(this, _picasso);
        _listView.setAdapter(_topAdapter);

        bind$(_topService.list()).subscribe(new Observer<Top.Result>() {
            @Override
            public void onCompleted() {
                Timber.i("### onCompleted.");
                _toastHelper.show("App version is " + _versionName + "-" + _versionCode);
            }

            @Override
            public void onError(Throwable e) {
                ApiException exception = ApiException.create(e, _retrofit);
                // Do what you want with exception.

                _toastHelper.show(exception.getMessage());
            }

            @Override
            public void onNext(Top.Result result) {
                _topAdapter.setResult(result.getTngou());
            }
        });
    }

    @Override
    public void injectMembers() {
        getComponent().inject(this);
    }
}
