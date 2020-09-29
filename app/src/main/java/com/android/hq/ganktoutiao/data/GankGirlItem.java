package com.android.hq.ganktoutiao.data;

import com.android.hq.ganktoutiao.data.bean.GankItemBean;

public class GankGirlItem extends GankContentItem {
    public float ratio;
    public GankGirlItem(GankItemBean bean) {
        super(bean);
        ratio = 0.6f;
    }
}
