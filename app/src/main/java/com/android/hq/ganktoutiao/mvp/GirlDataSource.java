package com.android.hq.ganktoutiao.mvp;

import com.android.hq.ganktoutiao.data.GankItem;

import java.util.List;

public class GirlDataSource implements BaseDataSource {
    private List<GankItem> mCacheList;
    private BaseDataSource mRemoteDataSource;
    private static GirlDataSource INSTANCE;

    public static GirlDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GirlDataSource();
        }
        return INSTANCE;
    }

    private GirlDataSource() {
        mRemoteDataSource = new GirlRemoteDataSource();
    }

    @Override
    public void loadData(LoadDataCallback callback) {
        if (mCacheList != null && mCacheList.size() > 0) {
            callback.onLoaded(mCacheList);
            return;
        }
        mRemoteDataSource.loadData(callback);
    }

    @Override
    public void loadMoreData(int page, LoadDataCallback callback) {
        mRemoteDataSource.loadMoreData(page, callback);
    }

    @Override
    public void updateData(List<GankItem> list) {
        mCacheList = list;
    }
}
