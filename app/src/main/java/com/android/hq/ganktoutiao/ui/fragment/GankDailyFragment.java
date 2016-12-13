package com.android.hq.ganktoutiao.ui.fragment;

import android.util.Log;

import com.android.hq.ganktoutiao.data.GankContentItem;
import com.android.hq.ganktoutiao.data.GankHeaderItem;
import com.android.hq.ganktoutiao.data.GankImageItem;
import com.android.hq.ganktoutiao.data.GankItem;
import com.android.hq.ganktoutiao.data.GankType;
import com.android.hq.ganktoutiao.data.bean.DailyDataResponse;
import com.android.hq.ganktoutiao.data.bean.DailyDataBean;
import com.android.hq.ganktoutiao.data.bean.GankItemBean;
import com.android.hq.ganktoutiao.network.CallBack;
import com.android.hq.ganktoutiao.network.RequestManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heqiang on 16-9-2.
 */
public class GankDailyFragment extends BaseFragment {
    private final String LOG_TAG = "GankDailyFragment";

    @Override
    public void updateData(){
        RequestManager.getInstance().getDailyData(new CallBack<DailyDataResponse>() {
            @Override
            public void onSuccess(DailyDataResponse dailyDataResponse) {
                Log.e(LOG_TAG, "onSuccess");
                mRefreshLayout.setRefreshing(false);
                List<GankItem> list = new ArrayList<GankItem>();
                if(dailyDataResponse != null){
                    DailyDataBean dataResult = dailyDataResponse.results;
                    if(dataResult != null){
                        ArrayList<GankItemBean> benefitDataList = dataResult.benefitDataList;
                        if(benefitDataList != null && benefitDataList.size() != 0){
                            list.add(new GankHeaderItem(GankType.TYPE_BENEFIT));
                            list.add(new GankImageItem(benefitDataList.get(0).url));
                        }

                        ArrayList<GankItemBean> androidDataList = dataResult.androidDataList;
                        if(androidDataList != null && androidDataList.size() != 0){
                            list.add(new GankHeaderItem(GankType.TYPE_ANDROID));
                            for(GankItemBean bean : androidDataList){
                                list.add(new GankContentItem(bean));
                            }
                        }

                        ArrayList<GankItemBean> iosGDataList = dataResult.iosGDataList;
                        if(iosGDataList != null && iosGDataList.size() != 0){
                            list.add(new GankHeaderItem(GankType.TYPE_IOS));
                            for(GankItemBean bean : iosGDataList){
                                list.add(new GankContentItem(bean));
                            }
                        }

                        ArrayList<GankItemBean> webDataList = dataResult.webDataList;
                        if(webDataList != null && webDataList.size() != 0){
                            list.add(new GankHeaderItem(GankType.TYPE_WEB));
                            for(GankItemBean bean : webDataList){
                                list.add(new GankContentItem(bean));
                            }
                        }

                        ArrayList<GankItemBean> appDataList = dataResult.appDataList;
                        if(appDataList != null && appDataList.size() != 0){
                            list.add(new GankHeaderItem(GankType.TYPE_APP));
                            for(GankItemBean bean : appDataList){
                                list.add(new GankContentItem(bean));
                            }
                        }

                        ArrayList<GankItemBean> restVideoDataList = dataResult.restVideoDataList;
                        if(restVideoDataList != null && restVideoDataList.size() != 0){
                            list.add(new GankHeaderItem(GankType.TYPE_REST_VIDEO));
                            for(GankItemBean bean : restVideoDataList){
                                list.add(new GankContentItem(bean));
                            }
                        }

                        ArrayList<GankItemBean> expandResDataList = dataResult.expandResDataList;
                        if(expandResDataList != null && expandResDataList.size() != 0){
                            list.add(new GankHeaderItem(GankType.TYPE_EXPAND_RES));
                            for(GankItemBean bean : expandResDataList){
                                list.add(new GankContentItem(bean));
                            }
                        }

                        ArrayList<GankItemBean> recommendDataList = dataResult.recommendDataList;
                        if(recommendDataList != null && recommendDataList.size() != 0){
                            list.add(new GankHeaderItem(GankType.TYPE_RECOMMEND));
                            for(GankItemBean bean : recommendDataList){
                                list.add(new GankContentItem(bean));
                            }
                        }
                    }
                }
                mAdapter.updateData(list);
                updateSuccess(list.isEmpty());
            }

            @Override
            public void onFail() {
                mRefreshLayout.setRefreshing(false);
                updateError();
            }
        });
    }

    @Override
    public void loadMore() {

    }

    @Override
    public boolean isEnablePullRefresh() {
        return true;
    }

    @Override
    public boolean isEnableLoadingMore() {
        return false;
    }

    @Override
    public boolean isEnableRefreshOnViewCreate() {
        return true;
    }
}
