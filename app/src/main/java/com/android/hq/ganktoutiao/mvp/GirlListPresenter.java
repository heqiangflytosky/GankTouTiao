package com.android.hq.ganktoutiao.mvp;

import androidx.annotation.NonNull;

import com.android.hq.ganktoutiao.data.GankContentItem;
import com.android.hq.ganktoutiao.data.GankGirlItem;
import com.android.hq.ganktoutiao.data.GankItem;
import com.android.hq.ganktoutiao.data.GankType;
import com.android.hq.ganktoutiao.data.bean.GankDataResponse;
import com.android.hq.ganktoutiao.data.bean.GankItemBean;
import com.android.hq.ganktoutiao.network.CallBack;
import com.android.hq.ganktoutiao.network.RequestManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class GirlListPresenter implements GankListContract.Presenter {
    private GankListContract.View mView;
    private CompositeDisposable mCompositeDisposable;
    private GirlDataSource mDataSource;

    public GirlListPresenter(@NonNull GankListContract.View view, GirlDataSource dataSource) {
        mView = view;
        mView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
        mDataSource = dataSource;
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
        /*
        CallBack<GankDataResponse> callBack = new CallBack<GankDataResponse>() {
            @Override
            public void onSuccess(GankDataResponse gankDataResponse) {
                mView.setRefreshing(false);
                ArrayList<GankItem> list  = new ArrayList<GankItem>();
                if(gankDataResponse != null && gankDataResponse.data != null){
                    for (GankItemBean bean : gankDataResponse.data){
                        list.add(new GankGirlItem(bean));
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
        };
        RequestManager.getInstance().getGirlData(20,1,callBack);
        */
        mDataSource.loadData(new BaseDataSource.LoadDataCallback() {
            @Override
            public void onLoaded(List<GankItem> list) {
                mView.setRefreshing(false);
                mView.updateData(list);
                mView.updateSuccess(list.isEmpty());
            }

            @Override
            public void onError() {
                mView.setRefreshing(false);
                mView.updateError();
            }
        });
    }

    @Override
    public void loadMore(String type, int page) {
        /*
        CallBack<GankDataResponse> callBack = new CallBack<GankDataResponse>() {
            @Override
            public void onSuccess(GankDataResponse gankDataResponse) {
                ArrayList<GankItem> list = new ArrayList<GankItem>();
                if (gankDataResponse != null && gankDataResponse.data != null) {
                    for (GankItemBean bean : gankDataResponse.data) {
                        list.add(new GankGirlItem(bean));
                    }
                }
                mView.updateMoreData(list);
            }

            @Override
            public void onFail() {
                mView.updateLoadMoreError();
            }
        };
        RequestManager.getInstance().getGirlData(20,page,callBack);
        */
        mDataSource.loadMoreData(page, new BaseDataSource.LoadDataCallback() {
            @Override
            public void onLoaded(List<GankItem> list) {
                mView.updateMoreData(list);
            }

            @Override
            public void onError() {
                mView.updateLoadMoreError();
            }
        });
    }

    public void updateData(List<GankItem> list) {
        mDataSource.updateData(list);
    }
}
