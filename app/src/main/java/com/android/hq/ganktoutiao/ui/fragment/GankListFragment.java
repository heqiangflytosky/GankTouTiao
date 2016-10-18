package com.android.hq.ganktoutiao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.hq.ganktoutiao.R;
import com.android.hq.ganktoutiao.data.GankContentItem;
import com.android.hq.ganktoutiao.data.GankItem;
import com.android.hq.ganktoutiao.data.GankType;
import com.android.hq.ganktoutiao.data.bean.GankDataResponse;
import com.android.hq.ganktoutiao.data.bean.GankItemBean;
import com.android.hq.ganktoutiao.network.CallBack;
import com.android.hq.ganktoutiao.network.RequestManager;
import com.android.hq.ganktoutiao.ui.adapter.ListAdapter;

import java.util.ArrayList;

/**
 * Created by heqiang on 16-9-2.
 */
public abstract class GankListFragment extends BaseFragment {

    private int mCurrentPage = 0;

    @Override
    public void updateData(){
        RequestManager.getInstance().getGankData(getType(), 20, 1, new CallBack<GankDataResponse>() {
            @Override
            public void onSuccess(GankDataResponse gankDataResponse) {
                mRefreshLayout.setRefreshing(false);
                ArrayList<GankItem> list  = new ArrayList<GankItem>();
                if(gankDataResponse != null && gankDataResponse.results != null){
                    for (GankItemBean bean : gankDataResponse.results){
                        list.add(new GankContentItem(bean));
                    }
                }
                mAdapter.updateData(list);
                mCurrentPage = 1;
            }

            @Override
            public void onFail() {
                mRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void loadMore(){
        mAdapter.onLoadMore();
        RequestManager.getInstance().getGankData(getType(), 20,++mCurrentPage, new CallBack<GankDataResponse>() {
            @Override
            public void onSuccess(GankDataResponse gankDataResponse) {
                ArrayList<GankItem> list = new ArrayList<GankItem>();
                if (gankDataResponse != null && gankDataResponse.results != null) {
                    for (GankItemBean bean : gankDataResponse.results) {
                        list.add(new GankContentItem(bean));
                    }
                }
                mAdapter.loadMoreData(list);
                mLoadingMore = false;
            }

            @Override
            public void onFail() {
                mCurrentPage--;
                mAdapter.loadMoreData(null);
                mLoadingMore = false;
            }
        });
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

    public abstract String getType();
}
