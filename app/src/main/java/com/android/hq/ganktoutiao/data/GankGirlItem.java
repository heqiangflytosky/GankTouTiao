package com.android.hq.ganktoutiao.data;

import com.android.hq.ganktoutiao.data.bean.GankItemBean;

import java.util.Objects;

public class GankGirlItem extends GankContentItem {
    public float ratio;
    public GankGirlItem(GankItemBean bean) {
        super(bean);
        ratio = 0.6f;
    }
    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || this.getClass() != o.getClass())
            return false;
        GankGirlItem item = (GankGirlItem) o;
        return item._id.equals(this._id) && item.type.equals(this.type) && item.ratio == this.ratio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, type, ratio);
    }
}
