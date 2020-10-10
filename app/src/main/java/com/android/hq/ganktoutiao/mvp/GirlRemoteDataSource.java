package com.android.hq.ganktoutiao.mvp;

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

public class GirlRemoteDataSource implements BaseDataSource {
    @Override
    public void loadData(LoadDataCallback callback) {
        CallBack<GankDataResponse> callBack = new CallBack<GankDataResponse>() {
            @Override
            public void onSuccess(GankDataResponse gankDataResponse) {
                ArrayList<GankItem> list  = new ArrayList<GankItem>();
                if(gankDataResponse != null && gankDataResponse.data != null){
                    for (GankItemBean bean : gankDataResponse.data){
                        if (GankType.TYPE_GIRL.equals(bean.type)) {
                            list.add(new GankGirlItem(bean));
                        } else {
                            list.add(new GankContentItem(bean));
                        }
                    }
                }
                callback.onLoaded(list);
            }

            @Override
            public void onFail() {
                callback.onError();
            }
        };
        RequestManager.getInstance().getGirlData(20,1,callBack);
    }

    @Override
    public void loadMoreData(int page, LoadDataCallback callback) {
        CallBack<GankDataResponse> callBack = new CallBack<GankDataResponse>() {
            @Override
            public void onSuccess(GankDataResponse gankDataResponse) {
                ArrayList<GankItem> list = new ArrayList<GankItem>();
                if (gankDataResponse != null && gankDataResponse.data != null) {
                    for (GankItemBean bean : gankDataResponse.data) {
                        list.add(new GankGirlItem(bean));
                    }
                }
                callback.onLoaded(list);
            }

            @Override
            public void onFail() {
                callback.onError();
            }
        };
        RequestManager.getInstance().getGirlData(20,page,callBack);
    }

    @Override
    public void updateData(List<GankItem> list) {

    }
}
