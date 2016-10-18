package com.android.hq.ganktoutiao.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.hq.ganktoutiao.R;
import com.android.hq.ganktoutiao.ui.adapter.ListAdapter;

/**
 * Created by heqiang on 16-9-2.
 */
public abstract class BaseFragment extends Fragment {
    protected RecyclerView mRecyclerView;
    protected SwipeRefreshLayout mRefreshLayout;
    protected ListAdapter mAdapter;

    protected boolean mLoadingMore = false;
    private LinearLayoutManager mLinearLayoutManager;

    private boolean mCanLoadingMore = true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.refresh_recyclerview_fragment, null);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        mAdapter = new ListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mRefreshLayout.setColorSchemeResources(R.color.blue, R.color.green, R.color.orange);

        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mRefreshLayout.setEnabled(isEnablePullRefresh());
        mCanLoadingMore = isEnableLoadingMore();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(isEnableRefreshOnViewCreate()){
            mRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(true);
                    updateData();
                }
            });
        }
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            updateData();
        }
    };

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if(!mCanLoadingMore)
                return;
            int totalItemCount = mLinearLayoutManager.getItemCount();
            int lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
            if(!mLoadingMore && lastVisibleItemPosition >=totalItemCount -1){
                loadMore();
                mLoadingMore = true;
            }
        }
    };

    public abstract void updateData();
    public abstract void loadMore();
    public abstract boolean isEnablePullRefresh();
    public abstract boolean isEnableLoadingMore();
    public abstract boolean isEnableRefreshOnViewCreate();
}
