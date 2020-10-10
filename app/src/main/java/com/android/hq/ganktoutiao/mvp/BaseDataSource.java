package com.android.hq.ganktoutiao.mvp;

import com.android.hq.ganktoutiao.data.GankItem;

import java.util.List;

public interface BaseDataSource {
    interface LoadDataCallback {
        void onLoaded(List<GankItem> list);
        void onError();
    }

    void loadData(LoadDataCallback callback);
    void loadMoreData(int page, LoadDataCallback callback);
    void updateData(List<GankItem> list);
}
