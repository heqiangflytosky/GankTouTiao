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
import java.util.Collections;
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
                int maxLength = 5;
                if(dailyDataResponse != null){
                    DailyDataBean dataResult = dailyDataResponse.results;
                    if(dataResult != null){
                        ArrayList<GankItemBean> benefitDataList = dataResult.benefitDataList;
                        if(benefitDataList != null && benefitDataList.size() != 0){
                            list.add(new GankHeaderItem(GankType.TYPE_BENEFIT));
                            list.add(new GankImageItem(benefitDataList.get(benefitDataList.size() -1).url));
                        }

                        ArrayList<GankItemBean> androidDataList = dataResult.androidDataList;
                        if(androidDataList != null && androidDataList.size() != 0){
                            list.add(new GankHeaderItem(GankType.TYPE_ANDROID));
                            for (int i = androidDataList.size(); i > androidDataList.size() -maxLength; i--) {
                                if (i - 1 < 0) {
                                    break;
                                }
                                list.add(new GankContentItem(androidDataList.get(i -1)));
                            }
                        }

                        ArrayList<GankItemBean> iosGDataList = dataResult.iosGDataList;
                        if(iosGDataList != null && iosGDataList.size() != 0){
                            list.add(new GankHeaderItem(GankType.TYPE_IOS));
                            for (int i = iosGDataList.size(); i > iosGDataList.size() -maxLength; i--) {
                                if (i - 1 < 0) {
                                    break;
                                }
                                list.add(new GankContentItem(iosGDataList.get(i -1)));
                            }
                        }

                        ArrayList<GankItemBean> webDataList = dataResult.webDataList;
                        if(webDataList != null && webDataList.size() != 0){
                            list.add(new GankHeaderItem(GankType.TYPE_WEB));
                            for (int i = webDataList.size(); i > webDataList.size() -maxLength; i--) {
                                if (i - 1 < 0) {
                                    break;
                                }
                                list.add(new GankContentItem(webDataList.get(i -1)));
                            }
                        }

                        ArrayList<GankItemBean> appDataList = dataResult.appDataList;
                        if(appDataList != null && appDataList.size() != 0){
                            list.add(new GankHeaderItem(GankType.TYPE_APP));
                            for (int i = appDataList.size(); i > appDataList.size() -maxLength; i--) {
                                if (i - 1 < 0) {
                                    break;
                                }
                                list.add(new GankContentItem(appDataList.get(i -1)));
                            }
                        }

                        ArrayList<GankItemBean> restVideoDataList = dataResult.restVideoDataList;
                        if(restVideoDataList != null && restVideoDataList.size() != 0){
                            list.add(new GankHeaderItem(GankType.TYPE_REST_VIDEO));
                            for (int i = restVideoDataList.size(); i > restVideoDataList.size() -maxLength; i--) {
                                if (i - 1 < 0) {
                                    break;
                                }
                                list.add(new GankContentItem(restVideoDataList.get(i -1)));
                            }
                        }

                        ArrayList<GankItemBean> expandResDataList = dataResult.expandResDataList;
                        if(expandResDataList != null && expandResDataList.size() != 0){
                            list.add(new GankHeaderItem(GankType.TYPE_EXPAND_RES));
                            for (int i = expandResDataList.size(); i > expandResDataList.size() -maxLength; i--) {
                                if (i - 1 < 0) {
                                    break;
                                }
                                list.add(new GankContentItem(expandResDataList.get(i -1)));
                            }
                        }

                        ArrayList<GankItemBean> recommendDataList = dataResult.recommendDataList;
                        if(recommendDataList != null && recommendDataList.size() != 0){
                            list.add(new GankHeaderItem(GankType.TYPE_RECOMMEND));
                            for (int i = recommendDataList.size(); i > recommendDataList.size() -maxLength; i--) {
                                if (i - 1 < 0) {
                                    break;
                                }
                                list.add(new GankContentItem(recommendDataList.get(i -1)));
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
