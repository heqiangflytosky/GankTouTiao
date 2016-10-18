package com.android.hq.ganktoutiao.data;

import com.android.hq.ganktoutiao.data.bean.GankItemBean;

import java.util.Objects;

/**
 * Created by heqiang on 16-9-26.
 */
public class GankFooterItem extends GankItemBean implements GankItem {
    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || this.getClass() != o.getClass())
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(GankFooterItem.class);
    }
}
