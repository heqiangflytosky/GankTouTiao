package com.android.hq.ganktoutiao.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.hq.ganktoutiao.R;
import com.android.hq.ganktoutiao.provider.GankProviderHelper;
import com.android.hq.ganktoutiao.ui.activity.AboutMeActivity;
import com.android.hq.ganktoutiao.ui.activity.FavouriteActivity;
import com.android.hq.ganktoutiao.ui.activity.HistoryActivity;
import com.android.hq.ganktoutiao.ui.activity.MainActivity;
import com.android.hq.ganktoutiao.utils.BackgroundHandler;

/**
 * Created by heqiang on 16-10-13.
 */
public class AboutFragment extends Fragment implements View.OnClickListener{

    private TextView mTextViewFavPageCounts;
    private TextView mTextViewReadPageCounts;

    private View mLayoutFavour;
    private View mLayoutHistory;
    private View mLayoutAboutAuthor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initViews(inflater);
    }

    private View initViews(LayoutInflater inflater){
        View rootView = inflater.inflate(R.layout.fragment_about, null);
        mTextViewFavPageCounts = (TextView) rootView.findViewById(R.id.text_fav_pages);
        mTextViewReadPageCounts = (TextView) rootView.findViewById(R.id.text_read_history);

        mLayoutFavour = rootView.findViewById(R.id.layout_favourite);
        mLayoutHistory = rootView.findViewById(R.id.layout_read_history);
        mLayoutAboutAuthor = rootView.findViewById(R.id.layout_about_author);
        mLayoutFavour.setOnClickListener(this);
        mLayoutHistory.setOnClickListener(this);
        mLayoutAboutAuthor.setOnClickListener(this);

        mTextViewFavPageCounts.setText(getResources().getString(R.string.text_read_pages, 0));
        mTextViewReadPageCounts.setText(getResources().getString(R.string.text_read_pages, 0));
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateHistoryCount();
        updateFavouriteCount();
    }

    private void updateHistoryCount(){
        BackgroundHandler.execute(new Runnable() {
            @Override
            public void run() {
                final int count = GankProviderHelper.getInstance().getHistoryCount();
                if(getActivity() != null && !getActivity().isDestroyed()){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextViewReadPageCounts.setText(getResources().getString(R.string.text_read_pages, count));
                        }
                    });
                }
            }
        });
    }

    private void updateFavouriteCount(){
        BackgroundHandler.execute(new Runnable() {
            @Override
            public void run() {
                final int count = GankProviderHelper.getInstance().getFavouriteCount();
                if(getActivity() != null && !getActivity().isDestroyed()){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextViewFavPageCounts.setText(getResources().getString(R.string.text_read_pages,count));
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_favourite:
                Intent favIntent = new Intent(getActivity(), FavouriteActivity.class);
                getActivity().startActivity(favIntent);
                break;
            case R.id.layout_read_history:
                Intent historyIntent = new Intent(getActivity(), HistoryActivity.class);
                getActivity().startActivity(historyIntent);
                break;
            case R.id.layout_about_author:
                Intent aboutIntent = new Intent(getActivity(), AboutMeActivity.class);
                getActivity().startActivity(aboutIntent);
                break;
        }
    }
}
