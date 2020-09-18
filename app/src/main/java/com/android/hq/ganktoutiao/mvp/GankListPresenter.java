package com.android.hq.ganktoutiao.mvp;

import androidx.annotation.NonNull;

import com.android.hq.ganktoutiao.data.GankContentItem;
import com.android.hq.ganktoutiao.data.GankItem;
import com.android.hq.ganktoutiao.data.bean.GankDataResponse;
import com.android.hq.ganktoutiao.data.bean.GankItemBean;
import com.android.hq.ganktoutiao.network.CallBack;
import com.android.hq.ganktoutiao.network.RequestManager;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class GankListPresenter implements GankListContract.Presenter {

    private GankListContract.View mView;
    private CompositeDisposable mCompositeDisposable;

    public GankListPresenter(@NonNull GankListContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void loadData(String type) {
        RequestManager.getInstance().getGankData(type, 20, 1, new CallBack<GankDataResponse>() {
            @Override
            public void onSuccess(GankDataResponse gankDataResponse) {
                mView.setRefreshing(false);
                ArrayList<GankItem> list  = new ArrayList<GankItem>();
                if(gankDataResponse != null && gankDataResponse.data != null){
                    for (GankItemBean bean : gankDataResponse.data){
                        list.add(new GankContentItem(bean));
                    }
                }
                mView.updateData(list);
                mView.updateSuccess(list.isEmpty());
            }

            @Override
            public void onFail() {
                mView.setRefreshing(false);
                mView.updateError();
            }
        });
    }

    @Override
    public void loadMore(String type, int page) {
        RequestManager.getInstance().getGankData(type, 20,page, new CallBack<GankDataResponse>() {
            @Override
            public void onSuccess(GankDataResponse gankDataResponse) {
                ArrayList<GankItem> list = new ArrayList<GankItem>();
                if (gankDataResponse != null && gankDataResponse.data != null) {
                    for (GankItemBean bean : gankDataResponse.data) {
                        list.add(new GankContentItem(bean));
                    }
                }
                mView.updateMoreData(list);
            }

            @Override
            public void onFail() {
                mView.updateLoadMoreError();
            }
        });
    }
}
