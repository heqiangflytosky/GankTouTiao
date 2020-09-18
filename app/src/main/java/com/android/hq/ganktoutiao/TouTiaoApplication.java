package com.android.hq.ganktoutiao;

import android.app.Application;
import android.util.Log;

import com.android.hq.ganktoutiao.provider.GankProviderHelper;
import com.android.hq.ganktoutiao.utils.AppUtils;
import com.android.hq.ganktoutiao.utils.NetWorkObserver;
import com.facebook.stetho.Stetho;

/**
 * Created by heqiang on 16-10-8.
 */
public class TouTiaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);
        GankProviderHelper.createInstance(this);
        NetWorkObserver.init(this);
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}
