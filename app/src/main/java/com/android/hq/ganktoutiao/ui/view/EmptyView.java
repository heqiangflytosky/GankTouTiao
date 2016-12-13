package com.android.hq.ganktoutiao.ui.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.hq.ganktoutiao.R;

/**
 * Created by heqiang on 16-12-12.
 */
public class EmptyView extends FrameLayout {
    private TextView mTitleView;
    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        View view = LayoutInflater.from(context).inflate(R.layout.empty_view, null);
        mTitleView = (TextView) view.findViewById(R.id.empty_title);

        addView(view);
    }

    public void setTitle(CharSequence title) {
        if (TextUtils.isEmpty(title)) {
            mTitleView.setVisibility(GONE);
            return;
        }
        mTitleView.setText(title);
        mTitleView.setVisibility(VISIBLE);
    }
}
