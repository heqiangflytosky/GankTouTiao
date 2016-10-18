package com.android.hq.ganktoutiao.ui.fragment;

import com.android.hq.ganktoutiao.data.GankType;

/**
 * Created by heqiang on 16-9-26.
 */
public class GankAndroidFragment extends GankListFragment {
    @Override
    public String getType() {
        return GankType.TYPE_ANDROID;
    }
}
