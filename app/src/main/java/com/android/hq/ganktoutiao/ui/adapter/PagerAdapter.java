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
        private String tag;
        private Fragment fragment;

        public PageInfo(Fragment fragment){
            this.fragment = fragment;
        }
    }

    @Override
    public Fragment getItem(int position) {
        PageInfo info = mTabs.get(position);
        return info.fragment;
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    public void addPage(Fragment fragment){
        PageInfo info = new PageInfo(fragment);
        mTabs.add(info);
        notifyDataSetChanged();
    }
}
