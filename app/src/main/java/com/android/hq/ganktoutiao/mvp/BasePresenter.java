package com.android.hq.ganktoutiao.mvp;

public interface BasePresenter {
    void subscribe();
    void unsubscribe();
    void loadData(String type);
}
