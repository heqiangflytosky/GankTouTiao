package com.android.hq.ganktoutiao.mvp;

import com.android.hq.ganktoutiao.data.GankItem;

import java.util.List;

public interface GankListContract {
    interface Presenter extends BasePresenter {
        void loadMore(String type, int page);
    }
    interface GrilPresenter extends Presenter {
        void updateDate();
    }
    interface View extends BaseView<Presenter> {
        void setRefreshing(boolean refreshing);
        void updateData(List<GankItem> list);
        void updateMoreData(List<GankItem> list);
        void updateSuccess(boolean isEmpty);
        void updateError();
        void updateLoadMoreError();
    }
}
