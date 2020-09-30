package com.android.hq.ganktoutiao.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.hq.ganktoutiao.data.GankType;
import com.android.hq.ganktoutiao.mvp.GankListPresenter;
import com.android.hq.ganktoutiao.utils.Event;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ImageWallFragment extends GankListFragment {

    public static ImageWallFragment newInstance() {
        Bundle bundle = new Bundle();
        bundle.putString(GankListFragment.TYPE, GankType.TYPE_GIRL);
        ImageWallFragment fragment = new ImageWallFragment();
        fragment.setArguments(bundle);
        new GankListPresenter(fragment);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        // 覆盖 BaseFragment 中的 LayoutManager
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        EventBus.getDefault().register(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public String getType() {
        return GankType.TYPE_GIRL;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(Event event) {
        Log.e("Test","Receive event = "+event.msg);
        mAdapter.updateData(event.list);
        mRecyclerView.scrollToPosition(event.currentIndex);
    }
}
