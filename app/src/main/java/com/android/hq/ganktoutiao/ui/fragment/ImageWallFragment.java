package com.android.hq.ganktoutiao.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.hq.ganktoutiao.data.GankContentItem;
import com.android.hq.ganktoutiao.data.GankItem;
import com.android.hq.ganktoutiao.data.bean.DailyDataBean;
import com.android.hq.ganktoutiao.data.bean.GankDataResponse;
import com.android.hq.ganktoutiao.data.bean.GankItemBean;
import com.android.hq.ganktoutiao.network.CallBack;
import com.android.hq.ganktoutiao.network.RequestManager;

import java.util.ArrayList;
import java.util.List;

public class ImageWallFragment extends BaseFragment  {
    private final String LOG_TAG = "ImageWallFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        // 覆盖 BaseFragment 中的 LayoutManager
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        return view;
    }

    @Override
    public void updateData() {
        RequestManager.getInstance().getGirlData(20, 1, new CallBack<GankDataResponse>() {
            @Override
            public void onSuccess(GankDataResponse gankDataResponse) {
                Log.e(LOG_TAG, "onSuccess");
                mRefreshLayout.setRefreshing(false);
                List<GankItem> list = new ArrayList<GankItem>();

                if(gankDataResponse != null && gankDataResponse.data != null){
                    for (GankItemBean bean : gankDataResponse.data){
                        list.add(new GankContentItem(bean));
                    }
                }

                mAdapter.updateData(list);
                updateSuccess(list.isEmpty());
            }

            @Override
            public void onFail() {

            }
        });
    }

    @Override
    public void loadMore() {

    }

    @Override
    public boolean isEnablePullRefresh() {
        return true;
    }

    @Override
    public boolean isEnableLoadingMore() {
        return true;
    }

    @Override
    public boolean isEnableRefreshOnViewCreate() {
        return true;
    }
}
