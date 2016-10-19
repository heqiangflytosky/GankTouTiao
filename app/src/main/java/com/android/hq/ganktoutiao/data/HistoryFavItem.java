package com.android.hq.ganktoutiao.data;

import java.util.Objects;

/**
 * Created by heqiang on 16-10-21.
 */
public class HistoryFavItem extends GankDetailData implements GankItem {
    public HistoryFavItem(GankDetailData data){
        this.gank_id = data.gank_id;
        this.gank_type = data.gank_type;
        this.published_date = data.published_date;
        this.title = data.title;
        this.url = data.url;
        this.who = data.who;
        this.action_date = data.action_date;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || this.getClass() != o.getClass())
            return false;
        HistoryFavItem item = (HistoryFavItem) o;
        return item.gank_id.equals(this.gank_id) && item.gank_type.equals(this.gank_type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gank_id, gank_type);
    }
}
