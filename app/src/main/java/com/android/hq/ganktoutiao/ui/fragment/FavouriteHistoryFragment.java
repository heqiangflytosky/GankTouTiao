package com.android.hq.ganktoutiao.ui.fragment;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.android.hq.ganktoutiao.data.GankDetailData;
import com.android.hq.ganktoutiao.data.GankItem;
import com.android.hq.ganktoutiao.data.HistoryFavItem;
import com.android.hq.ganktoutiao.provider.GankContentProvider;
import com.android.hq.ganktoutiao.provider.GankProviderHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heqiang on 16-10-20.
 */
public class FavouriteHistoryFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<List<GankDetailData>> {
    public final static String FRAGMENT_TYPE = "FRAGMENT_TYPE";
    public final static int FRAGMENT_TYPE_HISTORY = 1;
    public final static int FRAGMENT_TYPE_FAV = 2;

    public final static String FRAGMENT_TAG_HISTORY = "FRAGMENT_TAG_HISTORY";
    public final static String FRAGMENT_TAG_FAVOURITE = "FRAGMENT_TAG_FAVOURITE";

    private final static int HISTORY_LOADER_ID = 1000;
    private final static int FAVOURITE_LOADER_ID = 1001;

    private ContentObserver mContentObserver;

    private int mType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt(FRAGMENT_TYPE);
        registerObserver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterObserver();
    }

    @Override
    public void updateData() {
        reloadData();
    }

    @Override
    public void loadMore() {

    }

    @Override
    public boolean isEnablePullRefresh() {
        return false;
    }

    @Override
    public boolean isEnableLoadingMore() {
        return false;
    }

    @Override
    public boolean isEnableRefreshOnViewCreate() {
        return true;
    }

    //---------------loader callback

    @Override
    public Loader<List<GankDetailData>> onCreateLoader(int id, Bundle bundle) {
        switch (id){
            case HISTORY_LOADER_ID:
                AsyncTaskLoader<List<GankDetailData>> historyLoader = new AsyncTaskLoader<List<GankDetailData>>(getActivity()) {
                    @Override
                    public List<GankDetailData> loadInBackground() {
                        return GankProviderHelper.getInstance().queryHistories();
                    }

                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                        forceLoad();
                    }
                };
                return historyLoader;
            case FAVOURITE_LOADER_ID:
                AsyncTaskLoader<List<GankDetailData>> favouritesLoader = new AsyncTaskLoader<List<GankDetailData>>(getActivity()) {
                    @Override
                    public List<GankDetailData> loadInBackground() {
                        return GankProviderHelper.getInstance().queryFavourites();
                    }

                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                        forceLoad();
                    }
                };
                return favouritesLoader;

             default:
                break;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<GankDetailData>> loader, List<GankDetailData> gankDetailDatas) {
        switch (loader.getId()){
            case HISTORY_LOADER_ID:
                mRefreshLayout.setRefreshing(false);
                ArrayList<GankItem> history_list  = new ArrayList<GankItem>();
                if(gankDetailDatas != null){
                    if(gankDetailDatas.isEmpty()){
                        updateState(STATE_HISTORY_EMPTY);
                    }else{
                        for (GankDetailData bean : gankDetailDatas){
                            history_list.add(new HistoryFavItem(bean));
                        }
                    }
                }
                mAdapter.forceUpdateData(history_list);
                getLoaderManager().destroyLoader(HISTORY_LOADER_ID);
                break;
            case FAVOURITE_LOADER_ID:
                mRefreshLayout.setRefreshing(false);
                ArrayList<GankItem> fav_list  = new ArrayList<GankItem>();
                if(gankDetailDatas != null){
                    if(gankDetailDatas.isEmpty()){
                        updateState(STATE_FAVOURITE_EMPTY);
                    }else{
                        for (GankDetailData bean : gankDetailDatas){
                            fav_list.add(new HistoryFavItem(bean));
                        }
                    }
                }
                mAdapter.forceUpdateData(fav_list);
                getLoaderManager().destroyLoader(FAVOURITE_LOADER_ID);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<List<GankDetailData>> loader) {
        getLoaderManager().destroyLoader(loader.getId());
    }

    private void reloadData(){
        if(getActivity() != null){
            if(getType() == FRAGMENT_TYPE_HISTORY){
                getLoaderManager().restartLoader(HISTORY_LOADER_ID, null, this);
            }else if(getType() == FRAGMENT_TYPE_FAV){
                getLoaderManager().restartLoader(FAVOURITE_LOADER_ID, null, this);
            }
        }
    }

    private void registerObserver(){
        if(getActivity() != null){
            Uri uri = null;
            if(getType() == FRAGMENT_TYPE_HISTORY){
                uri = GankContentProvider.HistoryProvider.URI_HISTORY;
            }else if(getType() == FRAGMENT_TYPE_FAV){
                uri = GankContentProvider.FavouriteProvider.URI_FAVOURITE;
            }
            mContentObserver = new ContentObserver(new Handler()) {
                @Override
                public void onChange(boolean selfChange, Uri uri) {
                    super.onChange(selfChange, uri);
                    mRefreshLayout.setRefreshing(true);
                    reloadData();
                }
            };
            getActivity().getContentResolver().registerContentObserver(uri, true, mContentObserver);
        }

    }

    private void unregisterObserver(){
        if(getActivity() != null && mContentObserver != null){
            getActivity().getContentResolver().unregisterContentObserver(mContentObserver);
        }
    }

    private int getType(){
        return mType;
    }
}
