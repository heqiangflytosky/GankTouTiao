package com.android.hq.ganktoutiao.data;

import com.android.hq.ganktoutiao.data.bean.GankSearchItemBean;

import java.util.Objects;

/**
 * Created by heqiang on 16-10-14.
 */
public class GankSearchItem extends GankSearchItemBean implements GankItem {
    public GankSearchItem(GankSearchItemBean bean){
        this.title = bean.title;
        this.desc = bean.desc;
        this.ganhuo_id = bean.ganhuo_id;
        this.publishedAt = bean.publishedAt;
        this.readability = bean.readability;
        this.type = bean.type;
        this.url = bean.url;
        this.author = bean.author;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || this.getClass() != o.getClass())
            return false;
        GankSearchItem item = (GankSearchItem) o;
        return item.ganhuo_id.equals(this.ganhuo_id) && item.type.equals(this.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ganhuo_id, type);
    }
}
