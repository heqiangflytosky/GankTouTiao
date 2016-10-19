package com.android.hq.ganktoutiao.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.android.hq.ganktoutiao.R;

/**
 * Created by heqiang on 16-10-18.
 */
public class AboutMeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
    }

    public void onBackClick(View v){
        finish();
    }
}
