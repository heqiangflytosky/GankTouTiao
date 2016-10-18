package com.android.hq.ganktoutiao.ui.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by heqiang on 16-9-1.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    private final Context mContext;
    private final ArrayList<PageInfo> mTabs = new ArrayList<PageInfo>();

    public PagerAdapter(Activity activity) {
        super(activity.getFragmentManager());
        mContext = activity;
    }

    public static class PageInfo{
        private final Class<?> clss;
        private String tag;
        private Fragment fragment;
        private final Bundle args;

        public PageInfo(Class<?> _clss, Bundle _args){
            clss = _clss;
            args = _args;
        }
    }

    @Override
    public Fragment getItem(int position) {
        PageInfo info = mTabs.get(position);
        Fragment fragment = Fragment.instantiate(mContext, info.clss.getName(), info.args);
        info.fragment = fragment;
        return fragment;
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    public void addPage(Class<?> clss, Bundle args){
        PageInfo info = new PageInfo(clss, args);
        mTabs.add(info);
        notifyDataSetChanged();
    }
}
