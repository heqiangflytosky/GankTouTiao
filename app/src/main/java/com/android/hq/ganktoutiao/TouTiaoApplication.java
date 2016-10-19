package com.android.hq.ganktoutiao;

import android.app.Application;

import com.android.hq.ganktoutiao.provider.GankProviderHelper;
import com.android.hq.ganktoutiao.utils.AppUtils;

/**
 * Created by heqiang on 16-10-8.
 */
public class TouTiaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);
        GankProviderHelper.createInstance(this);
    }
}
