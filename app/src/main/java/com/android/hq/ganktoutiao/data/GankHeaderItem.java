package com.android.hq.ganktoutiao.data;

import com.android.hq.ganktoutiao.data.bean.GankItemBean;

import java.util.Objects;

/**
 * Created by heqiang on 16-9-22.
 */
public class GankHeaderItem extends GankItemBean implements GankItem {
    public String name;
    public GankHeaderItem (String title){
        this.name = title;
    }
    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || this.getClass() != o.getClass())
            return false;
        GankHeaderItem item = (GankHeaderItem) o;
        return item.name.equals(this.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
