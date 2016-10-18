package com.android.hq.ganktoutiao.data;

import java.io.Serializable;

/**
 * Created by heqiang on 16-10-14.
 */
public class GankDetailData implements Serializable{
    public String url;
    public String who;
    public String title;
    public GankDetailData(String url, String who, String title){
        this.url = url;
        this.who = who;
        this.title = title;
    }
}
