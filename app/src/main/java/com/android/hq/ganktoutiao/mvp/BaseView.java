package com.android.hq.ganktoutiao.mvp;

import com.android.hq.ganktoutiao.data.GankItem;

import java.util.List;

public interface BaseView<T> {
    void setPresenter(T presenter);
}
