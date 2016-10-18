package com.android.hq.ganktoutiao.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by heqiang on 16-10-14.
 */
public class SizeObserverLinearLayout extends LinearLayout {
    private Context mContext;
    private OnSizeWillChangeListener mL;
    private int mLastMeasuredHeight = 0;

    public interface OnSizeWillChangeListener {
        void onSizeWillChanged(int w, int h);
    }
    public SizeObserverLinearLayout(Context context) {
        super(context);
    }

    public SizeObserverLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SizeObserverLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (mLastMeasuredHeight != measureHeight) {
            if (mL != null) {
                mL.onSizeWillChanged(measureWidth, measureHeight);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mLastMeasuredHeight = measureHeight;
    }

    public void setOnSizeChangeListener(OnSizeWillChangeListener l) {
        mL = l;
    }
}
