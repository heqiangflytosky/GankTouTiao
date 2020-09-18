package com.android.hq.ganktoutiao.data;

import com.android.hq.ganktoutiao.data.bean.GankItemBean;

import java.util.Objects;

/**
 * Created by heqiang on 16-9-22.
 */
public class GankContentItem extends GankItemBean implements GankItem {

    public GankContentItem(GankItemBean bean){
        this._id = bean._id;
        this.createdAt = bean.createdAt;
        this.title = bean.title;
        this.desc = bean.desc;
        this.publishedAt = bean.publishedAt;
        this.source = bean.source;
        this.type = bean.type;
        this.url = bean.url;
        this.used = bean.used;
        this.author = bean.author;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || this.getClass() != o.getClass())
            return false;
        GankContentItem item = (GankContentItem) o;
        return item._id.equals(this._id) && item.type.equals(this.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, type);
    }
}
