package com.android.hq.ganktoutiao.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.hq.ganktoutiao.R;
import com.android.hq.ganktoutiao.ui.fragment.FavouriteHistoryFragment;

/**
 * Created by heqiang on 16-10-25.
 */
public class FavouriteHistoryActivity extends AppCompatActivity {
    public final static int ACTIVITY_TYPE_HISTORY = 1;
    public final static int ACTIVITY_TYPE_FAV = 2;
    public final static String ACTIVITY_TYPE = "ACTIVITY_TYPE";
    private TextView mTitle;
    private int mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_history_layout);
        mTitle = (TextView) findViewById(R.id.menu_title);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = new FavouriteHistoryFragment();
        String fragmentTag = "";
        Bundle bundle = new Bundle();

        Intent intent = getIntent();
        int mType = intent.getIntExtra(ACTIVITY_TYPE, 0);
        if (mType == ACTIVITY_TYPE_HISTORY){
            mTitle.setText(getString(R.string.text_my_read_history));
            fragmentTag = FavouriteHistoryFragment.FRAGMENT_TAG_HISTORY;
            bundle.putInt(FavouriteHistoryFragment.FRAGMENT_TYPE, FavouriteHistoryFragment.FRAGMENT_TYPE_HISTORY);
        }else if(mType == ACTIVITY_TYPE_FAV){
            mTitle.setText(getString(R.string.text_my_collection));
            fragmentTag = FavouriteHistoryFragment.FRAGMENT_TAG_FAVOURITE;
            bundle.putInt(FavouriteHistoryFragment.FRAGMENT_TYPE, FavouriteHistoryFragment.FRAGMENT_TYPE_FAV);
        }

        fragment.setArguments(bundle);
        ft.replace(R.id.fragment_container, fragment, fragmentTag);
        ft.commit();
    }

    public void onBackClick(View v){
        finish();
    }
}
