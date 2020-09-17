package com.android.hq.ganktoutiao.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.hq.ganktoutiao.R;
import com.android.hq.ganktoutiao.provider.GankProviderHelper;
import com.android.hq.ganktoutiao.ui.activity.AboutMeActivity;
import com.android.hq.ganktoutiao.ui.activity.FavouriteHistoryActivity;
import com.android.hq.ganktoutiao.utils.BackgroundHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by heqiang on 16-10-13.
 */
public class AboutFragment extends Fragment{
    private Unbinder mUnbinder;

    @BindView(R.id.text_fav_pages)
    TextView mTextViewFavPageCounts;
    @BindView(R.id.text_read_history)
    TextView mTextViewReadPageCounts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = initViews(inflater);
        return rootView;
    }

    private View initViews(LayoutInflater inflater){
        View rootView = inflater.inflate(R.layout.fragment_about, null);
        mUnbinder = ButterKnife.bind(this, rootView);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
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

    @OnClick({R.id.layout_favourite, R.id.layout_read_history, R.id.layout_about_author})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_favourite:
                Intent favIntent = new Intent(getActivity(), FavouriteHistoryActivity.class);
                favIntent.putExtra(FavouriteHistoryActivity.ACTIVITY_TYPE, FavouriteHistoryActivity.ACTIVITY_TYPE_FAV);
                getActivity().startActivity(favIntent);
                break;
            case R.id.layout_read_history:
                Intent historyIntent = new Intent(getActivity(), FavouriteHistoryActivity.class);
                historyIntent.putExtra(FavouriteHistoryActivity.ACTIVITY_TYPE, FavouriteHistoryActivity.ACTIVITY_TYPE_HISTORY);
                getActivity().startActivity(historyIntent);
                break;
            case R.id.layout_about_author:
                Intent aboutIntent = new Intent(getActivity(), AboutMeActivity.class);
                getActivity().startActivity(aboutIntent);
                break;
        }
    }
}
