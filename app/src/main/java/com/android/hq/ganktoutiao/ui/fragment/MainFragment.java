package com.android.hq.ganktoutiao.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.hq.ganktoutiao.R;
import com.android.hq.ganktoutiao.data.GankType;
import com.android.hq.ganktoutiao.ui.adapter.PagerAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private PagerAdapter mPagerAdapter;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //initActionBar();
        return initView(inflater);
    }

    private View initView(LayoutInflater inflater){
        View rootView = inflater.inflate(R.layout.fragment_main, null, false);
        mViewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) rootView.findViewById(R.id.tabs);

        mPagerAdapter = new PagerAdapter(getActivity());

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mPagerAdapter);

        addPages();
        addTabs();

        return rootView;
    }

    private void initActionBar(){
        getActivity().getActionBar().hide();
    }

    private void addPages(){
        //mPagerAdapter.addPage(new GankDailyFragment());

        mPagerAdapter.addPage(GankListFragment.newInstance(GankType.TYPE_ANDROID));

        mPagerAdapter.addPage(GankListFragment.newInstance(GankType.TYPE_IOS));

        mPagerAdapter.addPage(GankListFragment.newInstance(GankType.TYPE_WEB));

        mPagerAdapter.addPage(GankListFragment.newInstance(GankType.TYPE_APP));

        mPagerAdapter.addPage(GankListFragment.newInstance(GankType.TYPE_Flutter));

        mPagerAdapter.addPage(GankListFragment.newInstance(GankType.TYPE_GIRL));
    }

    private void addTabs(){
        int defaultID;

        //addTab(R.string.tab_daily_recommend, 0, true);
        addTab(R.string.tab_daily_android, 0, true);
        addTab(R.string.tab_daily_ios, 1, false);
        addTab(R.string.tab_daily_web, 2, false);
        addTab(R.string.tab_daily_app, 3, false);
        addTab(R.string.tab_daily_flutter, 4, false);
        addTab(R.string.tab_girl, 5, false);

        mViewPager.setCurrentItem(0);
    }

    private void addTab(int textID, int position, boolean selected){
        TabLayout.Tab tab = mTabLayout.newTab();
//        LayoutInflater inflater = LayoutInflater.from(getActivity());
//        TextView view = (TextView) inflater.inflate(R.layout.top_tab_layout, null);
//        view.setText(textID);
//
//        tab.setCustomView(view);
        tab.setText(getResources().getString(textID));

        mTabLayout.addTab(tab, position, selected);
    }
}
