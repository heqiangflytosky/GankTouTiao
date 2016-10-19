package com.android.hq.ganktoutiao.data;

import java.io.Serializable;

/**
 * Created by heqiang on 16-10-14.
 */
public class GankDetailData implements Serializable{
    public String url;
    public String who;
    public String title;
    public String gank_id;
    public String gank_type;
    public String published_date;
    public long action_date;
    public GankDetailData(String id, String type, String url, String who, String title, String published_date, long action_date){
        this.gank_id = id;
        this.gank_type = type;
        this.url = url;
        this.who = who;
        this.title = title;
        this.published_date = published_date;
        this.action_date = action_date;
    }
    public GankDetailData(){

    }
}
