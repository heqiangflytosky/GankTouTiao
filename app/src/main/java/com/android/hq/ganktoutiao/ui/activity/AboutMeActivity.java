package com.android.hq.ganktoutiao.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.hq.ganktoutiao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by heqiang on 16-10-18.
 */
public class AboutMeActivity extends AppCompatActivity {
    private Unbinder mUnbinder;

    @BindView(R.id.menu_title)
    TextView mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        mUnbinder = ButterKnife.bind(this);
        mTitle.setText(getString(R.string.activity_title_about_me));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    public void onBackClick(View v){
        finish();
    }
}
