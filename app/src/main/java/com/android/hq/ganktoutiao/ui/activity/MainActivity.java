package com.android.hq.ganktoutiao.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hq.ganktoutiao.R;
import com.android.hq.ganktoutiao.ui.adapter.PagerAdapter;
import com.android.hq.ganktoutiao.ui.fragment.AboutFragment;
import com.android.hq.ganktoutiao.ui.fragment.MainFragment;
import com.android.hq.ganktoutiao.ui.fragment.PresentFragment;
import com.android.hq.ganktoutiao.ui.fragment.SearchFragment;
import com.android.hq.ganktoutiao.ui.view.SizeObserverLinearLayout;
import com.android.hq.ganktoutiao.ui.view.ViewPagerEx;

public class MainActivity extends Activity implements View.OnClickListener{

    private SizeObserverLinearLayout mRootView;
    private ViewPagerEx mViewPager;
    private PagerAdapter mPagerAdapter;
    private ViewGroup mToolBar;
    private View mHome;
    private View mPage2;
    private View mPage3;
    private View mPage4;

    private ImageView mHomeImg;
    private ImageView mPage2Img;
    private ImageView mPage3Img;
    private ImageView mPage4Img;

    private TextView mHomeTv;
    private TextView mPage2Tv;
    private TextView mPage3Tv;
    private TextView mPage4Tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        mRootView = (SizeObserverLinearLayout) findViewById(R.id.root_view);
        mViewPager = (ViewPagerEx) findViewById(R.id.view_pager_main);
        mToolBar = (ViewGroup) findViewById(R.id.tool_bar);
        mPagerAdapter = new PagerAdapter(this);

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCanScroll(false);
        mViewPager.setOffscreenPageLimit(3);

        mPagerAdapter.addPage(MainFragment.class, null);
        mPagerAdapter.addPage(SearchFragment.class, null);
        mPagerAdapter.addPage(PresentFragment.class, null);
        mPagerAdapter.addPage(AboutFragment.class, null);

        mHome = findViewById(R.id.page1);
        mPage2 = findViewById(R.id.page2);
        mPage3 = findViewById(R.id.page3);
        mPage4 = findViewById(R.id.page4);

        mHome.setOnClickListener(this);
        mPage2.setOnClickListener(this);
        mPage3.setOnClickListener(this);
        mPage4.setOnClickListener(this);

        mHomeImg = (ImageView) mToolBar.findViewById(R.id.home_img);
        mPage2Img = (ImageView) mToolBar.findViewById(R.id.page2_img);
        mPage3Img = (ImageView) mToolBar.findViewById(R.id.page3_img);
        mPage4Img = (ImageView) mToolBar.findViewById(R.id.page4_img);

        mHomeTv = (TextView) mToolBar.findViewById(R.id.home_tv);
        mPage2Tv = (TextView) mToolBar.findViewById(R.id.page2_tv);
        mPage3Tv = (TextView) mToolBar.findViewById(R.id.page3_tv);
        mPage4Tv = (TextView) mToolBar.findViewById(R.id.page4_tv);

        resetButton();

        mViewPager.setCurrentItem(0);
        mHomeImg.setImageResource(R.drawable.tab_home);
        mHomeTv.setTextColor(0xff508aeb);

        mRootView.setOnSizeChangeListener(new SizeObserverLinearLayout.OnSizeWillChangeListener() {
            @Override
            public void onSizeWillChanged(int w, int h) {
                int heightDiff = mRootView.getRootView().getMeasuredHeight() - h;
                if (heightDiff > 100) {
                    mToolBar.setVisibility(View.GONE);
                } else {
                    mToolBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        resetButton();
        switch (view.getId()){
            case R.id.page1:
                mViewPager.setCurrentItem(0);
                mHomeImg.setImageResource(R.drawable.tab_home);
                mHomeTv.setTextColor(0xff508aeb);
                break;
            case R.id.page2:
                mViewPager.setCurrentItem(1);
                mPage2Img.setImageResource(R.drawable.tab_explore);
                mPage2Tv.setTextColor(0xff508aeb);
                break;
            case R.id.page3:
                mViewPager.setCurrentItem(2);
                mPage3Img.setImageResource(R.drawable.tab_recommend);
                mPage3Tv.setTextColor(0xff508aeb);
                break;
            case R.id.page4:
                mViewPager.setCurrentItem(3);
                mPage4Img.setImageResource(R.drawable.tab_profile);
                mPage4Tv.setTextColor(0xff508aeb);
                break;
        }
    }

    private void resetButton(){
        mHomeImg.setImageResource(R.drawable.tab_home_normal);
        mPage2Img.setImageResource(R.drawable.tab_explore_normal);
        mPage3Img.setImageResource(R.drawable.tab_recommend_normal);
        mPage4Img.setImageResource(R.drawable.tab_profile_normal);

        mHomeTv.setTextColor(0xffc8cbd4);
        mPage2Tv.setTextColor(0xffc8cbd4);
        mPage3Tv.setTextColor(0xffc8cbd4);
        mPage4Tv.setTextColor(0xffc8cbd4);
    }
}
