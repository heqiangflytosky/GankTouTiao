package com.android.hq.ganktoutiao.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * Created by heqiang on 16-12-13.
 */
public class NetWorkObserver {
    public interface NetworkListener {
        void onNetworkChanged(boolean connected, String type);
    }

    private static NetWorkObserver sInstance;
    private Context mAppContext;
    private WeakRefArrayList<NetworkListener> mListeners;
    private NetWorkChangeReceiver mReceiver;

    public static void init(Context context){
        if(sInstance == null){
            sInstance = new NetWorkObserver(context);
        }
    }

    private NetWorkObserver(Context context){
        mAppContext = context;
        mListeners = new WeakRefArrayList<>(20);
    }

    public static void register(NetworkListener l) {
        sInstance.registerListener(l);
    }

    public static void unRegister(NetworkListener l) {
        sInstance.unRegisterListener(l);
    }

    private void registerListener(NetworkListener l) {
        synchronized(mListeners) {
            if (mListeners.indexOf(l) < 0) {
                mListeners.add(l);
            }
        }
        if (mListeners.size() > 0 && mReceiver == null) {
            registerReceiver();
        }
    }

    private void unRegisterListener(NetworkListener l) {
        synchronized(mListeners) {
            mListeners.remove(l);
        }
        if (mListeners.size() <= 0 && mReceiver != null) {
            unregisterReceiver();
        }
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mReceiver = new NetWorkChangeReceiver();
        mAppContext.registerReceiver(mReceiver, filter);
    }

    private void unregisterReceiver() {
        mAppContext.unregisterReceiver(mReceiver);
        mReceiver = null;
    }

    public class NetWorkChangeReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isNetOn = AppUtils.isNetworkWorking(context);
            String type = AppUtils.getNetworkType(context);
            synchronized(mListeners) {
                for (int i = 0; i < mListeners.size(); i++) {
                    NetworkListener l = mListeners.get(i);
                    if (l != null) {
                        l.onNetworkChanged(isNetOn, type);
                    }
                }
            }
        }
    }
}
