package com.android.hq.ganktoutiao.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.hq.ganktoutiao.R;
import com.android.hq.ganktoutiao.ui.adapter.ListAdapter;
import com.android.hq.ganktoutiao.ui.view.EmptyView;
import com.android.hq.ganktoutiao.utils.NetWorkObserver;

/**
 * Created by heqiang on 16-9-2.
 */
public abstract class BaseFragment extends Fragment {
    public static final int STATE_LOADING = 0;
    public static final int STATE_UPDATE_SUCCESS = 1;
    public static final int STATE_UPDATE_ERROR = 2;
    public static final int STATE_UPDATE_EMPTY = 3;
    public static final int STATE_NETWORK_ERROR = 4;
    public static final int STATE_FAVOURITE_EMPTY = 5;
    public static final int STATE_HISTORY_EMPTY = 6;

    protected RecyclerView mRecyclerView;
    protected SwipeRefreshLayout mRefreshLayout;
    protected ListAdapter mAdapter;
    protected EmptyView mEmptyView;

    private boolean mLoadingMore = false;
    private LinearLayoutManager mLinearLayoutManager;

    private boolean mCanLoadingMore = true;
    private long mShowLoadMoreTipsTime;
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

        mEmptyView = (EmptyView) rootView.findViewById(R.id.empty_view);
        mEmptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forceRefreshDate();
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(isEnableRefreshOnViewCreate()){
            mRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    forceRefreshDate();
                }
            });
        }
        NetWorkObserver.register(mNetworkListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        NetWorkObserver.unRegister(mNetworkListener);
    }

    private void forceRefreshDate(){
        mRefreshLayout.setRefreshing(true);
        updateData();
    }

    public void updateSuccess(boolean isEmpty){
        if(isEmpty){
            updateState(STATE_UPDATE_EMPTY);
        } else {
            updateState(STATE_UPDATE_SUCCESS);
        }
    }

    public void updateError(){
        updateState(STATE_UPDATE_ERROR);
    }

    public void loadMoreSuccess(boolean isEmpty){
        mLoadingMore = false;
        long currentTime = SystemClock.uptimeMillis();
        if(isEmpty && (currentTime - mShowLoadMoreTipsTime > 2000)){
            mShowLoadMoreTipsTime = currentTime;
            Toast.makeText(getActivity(), R.string.empty_view_load_more_empty, Toast.LENGTH_SHORT).show();
        }
    }

    public void loadMoreError(){
        mLoadingMore = false;
        long currentTime = SystemClock.uptimeMillis();
        if(currentTime - mShowLoadMoreTipsTime > 2000){
            mShowLoadMoreTipsTime = currentTime;
            Toast.makeText(getActivity(), R.string.empty_view_load_more_error, Toast.LENGTH_SHORT).show();
        }
    }

    public void updateState(int state){
        boolean showEmpty = false;
        boolean clickable = true;
        String title = null;
        switch (state){
            case STATE_LOADING:
            case STATE_UPDATE_SUCCESS:
                showEmpty = false;
                break;
            case STATE_UPDATE_EMPTY:
                showEmpty = true;
                title = getString(R.string.empty_view_update_empty);
                break;
            case STATE_UPDATE_ERROR:
                showEmpty = true;
                title = getString(R.string.empty_view_update_error);
                break;
            case STATE_NETWORK_ERROR:
                //showEmpty = true;
                //title = getString(R.string.empty_view_network_error);
                break;
            case STATE_FAVOURITE_EMPTY:
            case STATE_HISTORY_EMPTY:
                showEmpty = true;
                title = getString(R.string.empty_view_empty_data);
                clickable = false;
                break;
        }
        mEmptyView.setVisibility(showEmpty ? View.VISIBLE : View.GONE);
        mEmptyView.setClickable(clickable);
        if(showEmpty){
            mEmptyView.setTitle(title);
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
            boolean isScrollUp = dy > 0 ? true : false;
            int totalItemCount = mLinearLayoutManager.getItemCount();
            int lastVisibleItemPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
            if(!mLoadingMore && isScrollUp && lastVisibleItemPosition >=totalItemCount -1){
                loadMore();
                mLoadingMore = true;
            }
        }
    };

    private NetWorkObserver.NetworkListener mNetworkListener = new NetWorkObserver.NetworkListener() {
        @Override
        public void onNetworkChanged(boolean connected, String type) {
//            if(connected){
//                forceRefreshDate();
//            } else {
//                updateState(STATE_NETWORK_ERROR);
//            }
        }
    };

    public abstract void updateData();
    public abstract void loadMore();
    public abstract boolean isEnablePullRefresh();
    public abstract boolean isEnableLoadingMore();
    public abstract boolean isEnableRefreshOnViewCreate();
}
