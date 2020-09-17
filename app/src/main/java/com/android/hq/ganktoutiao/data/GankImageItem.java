package com.android.hq.ganktoutiao.data;

import com.android.hq.ganktoutiao.data.bean.GankItemBean;

import java.util.Objects;

/**
 * Created by heqiang on 16-9-23.
 */
public class GankImageItem extends GankItemBean implements GankItem {
    public String imageUrl;
    public GankImageItem(String url){
        imageUrl = url;
    }
    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || this.getClass() != o.getClass()) {
            return false;
        }
        GankImageItem item = (GankImageItem) o;
        return item.imageUrl.equals(this.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageUrl);
    }
}
