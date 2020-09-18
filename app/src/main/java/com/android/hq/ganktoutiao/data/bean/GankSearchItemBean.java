package com.android.hq.ganktoutiao.data.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by heqiang on 16-10-14.
 */
public class GankSearchItemBean  {
    public String title;
    public String desc;
    @SerializedName("_id")
    public String ganhuo_id;
    public String publishedAt;
    public String readability;
    public String type;
    public String url;
    public String author;
}
