package com.android.hq.ganktoutiao.network;

/**
 * Created by heqiang on 16-9-6.
 */
public interface CallBack<T> {
    void onSuccess(T t);
    void onFail();
}
