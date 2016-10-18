package com.android.hq.ganktoutiao.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.hq.ganktoutiao.R;

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

        mTextViewFavPageCounts.setText(getResources().getString(R.string.text_read_pages,0));
        mTextViewReadPageCounts.setText(getResources().getString(R.string.text_read_pages,0));
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_favourite:

                break;
            case R.id.layout_read_history:

                break;
            case R.id.layout_about_author:

                break;
        }
    }
}
