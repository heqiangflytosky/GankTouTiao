package com.android.hq.ganktoutiao.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by heqiang on 16-10-20.
 */
public class BackgroundHandler {
    private static HandlerThread sLooperThread;
    private static ExecutorService mThreadPool;
    private static Handler sHandler;

    static {
        sLooperThread = new HandlerThread("BackgroundHandler", HandlerThread.MIN_PRIORITY);
        sLooperThread.start();
        sHandler = new Handler(sLooperThread.getLooper());
        mThreadPool = Executors.newCachedThreadPool();
    }

    public static void post(Runnable runnable) {
        sHandler.post(runnable);
    }

    public static void execute(Runnable runnable) {
        mThreadPool.execute(runnable);
    }

    public static Looper getLooper() {
        return sLooperThread.getLooper();
    }

    private BackgroundHandler() {}
}
