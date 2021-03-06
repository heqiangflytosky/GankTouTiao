package com.android.hq.ganktoutiao.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.hq.ganktoutiao.data.GankItem;
import com.android.hq.ganktoutiao.data.GankType;
import com.android.hq.ganktoutiao.mvp.GankListContract;
import com.android.hq.ganktoutiao.mvp.GankListPresenter;

import java.util.List;

/**
 * Created by heqiang on 16-9-2.
 */
public class GankListFragment extends BaseFragment implements GankListContract.View {

    public static final String TYPE = "type";
    private int mCurrentPage = 0;
    private String mType;
    protected GankListContract.Presenter mPresenter;

    public static GankListFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString(GankListFragment.TYPE, type);
        GankListFragment fragment = new GankListFragment();
        fragment.setArguments(bundle);
        new GankListPresenter(fragment);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mType = bundle != null ? bundle.getString(TYPE) : null;
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = super.onCreateView(inflater, container, savedInstanceState);
//        if (GankType.TYPE_GIRL.equals(mType)) {
//            // 覆盖 BaseFragment 中的 LayoutManager
//            mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//            mRecyclerView.setLayoutManager(mLayoutManager);
//        }
//        return view;
//    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void updateData() {
        mPresenter.loadData(getType());
    }

    @Override
    public void loadMore(){
        mAdapter.onLoadMore();
        mPresenter.loadMore(getType(), ++mCurrentPage);
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

    @Override
    public void setRefreshing(boolean refreshing) {
        mRefreshLayout.setRefreshing(refreshing);
    }

    @Override
    public void setPresenter(GankListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void updateData(List<GankItem> list) {
        mAdapter.updateData(list);
        mCurrentPage = 1;
    }

    @Override
    public void updateLoadMoreError() {
        mCurrentPage--;
        mAdapter.loadMoreData(null);
        loadMoreError();
    }

    @Override
    public void updateMoreData(List<GankItem> list) {
        mAdapter.loadMoreData(list);
        loadMoreSuccess(list.isEmpty());
    }

    public String getType() {
        return mType;
    }
}
