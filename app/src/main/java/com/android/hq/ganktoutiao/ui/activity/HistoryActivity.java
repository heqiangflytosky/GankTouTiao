package com.android.hq.ganktoutiao.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.hq.ganktoutiao.R;

/**
 * Created by heqiang on 16-10-20.
 */
public class HistoryActivity extends Activity {
    private TextView mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_layout);
        mTitle = (TextView) findViewById(R.id.menu_title);
        mTitle.setText(getString(R.string.text_my_read_history));
    }

    public void onBackClick(View v){
        finish();
    }
}
