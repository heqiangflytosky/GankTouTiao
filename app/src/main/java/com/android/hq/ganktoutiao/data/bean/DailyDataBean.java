package com.android.hq.ganktoutiao.data.bean;

import com.android.hq.ganktoutiao.data.GankType;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by heqiang on 16-9-6.
 */
public class DailyDataBean {
    @SerializedName(GankType.TYPE_ANDROID)
    public ArrayList<GankItemBean> androidDataList;
    @SerializedName(GankType.TYPE_IOS)
    public ArrayList<GankItemBean> iosGDataList;
    @SerializedName(GankType.TYPE_WEB)
    public ArrayList<GankItemBean> webDataList;
    @SerializedName(GankType.TYPE_APP)
    public ArrayList<GankItemBean> appDataList;
    @SerializedName(GankType.TYPE_BENEFIT)
    public ArrayList<GankItemBean> benefitDataList;
    @SerializedName(GankType.TYPE_REST_VIDEO)
    public ArrayList<GankItemBean> restVideoDataList;
    @SerializedName(GankType.TYPE_EXPAND_RES)
    public ArrayList<GankItemBean> expandResDataList;
    @SerializedName(GankType.TYPE_RECOMMEND)
    public ArrayList<GankItemBean> recommendDataList;
}
