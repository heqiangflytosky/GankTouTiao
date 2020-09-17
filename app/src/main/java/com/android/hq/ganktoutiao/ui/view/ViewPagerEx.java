package com.android.hq.ganktoutiao.ui.view;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by heqiang on 16-9-27.
 */
public class ViewPagerEx extends ViewPager {

    private boolean mCanScroll = true;
    public ViewPagerEx(Context context) {
        super(context);
    }

    public ViewPagerEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanScroll(boolean canScroll) {
        mCanScroll = canScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mCanScroll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mCanScroll && super.onTouchEvent(ev);
    }
}
