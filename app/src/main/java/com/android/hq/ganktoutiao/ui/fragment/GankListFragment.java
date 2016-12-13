package com.android.hq.ganktoutiao.ui.fragment;

import com.android.hq.ganktoutiao.data.GankContentItem;
import com.android.hq.ganktoutiao.data.GankItem;
import com.android.hq.ganktoutiao.data.bean.GankDataResponse;
import com.android.hq.ganktoutiao.data.bean.GankItemBean;
import com.android.hq.ganktoutiao.network.CallBack;
import com.android.hq.ganktoutiao.network.RequestManager;

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
                updateSuccess(list.isEmpty());
            }

            @Override
            public void onFail() {
                mRefreshLayout.setRefreshing(false);
                updateError();
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
                loadMoreSuccess(list.isEmpty());
            }

            @Override
            public void onFail() {
                mCurrentPage--;
                mAdapter.loadMoreData(null);
                loadMoreError();
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
