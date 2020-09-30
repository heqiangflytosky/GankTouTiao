package com.android.hq.ganktoutiao.utils;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.Browser;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.TextView;

import com.android.hq.ganktoutiao.R;
import com.android.hq.ganktoutiao.data.GankDetailData;
import com.android.hq.ganktoutiao.data.GankItem;
import com.android.hq.ganktoutiao.provider.GankProviderHelper;
import com.android.hq.ganktoutiao.ui.activity.ArticleDetailActivity;
import com.android.hq.ganktoutiao.ui.activity.ImageBrowserActivity;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

import java.io.File;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by heqiang on 16-10-8.
 */
public class AppUtils {
    private static Context mAppContext;
    private static SimpleDateFormat sDataFormatZ = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static SimpleDateFormat sDataFormat6S = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
    private static SimpleDateFormat sDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final String TYPE_WIFI = "wifi";
    public static final String TYPE_2G = "2g";
    public static final String TYPE_3G = "3g";
    public static final String TYPE_4G = "4g";
    public static final String TYPE_OFF = "off";
    public static final String TYPE_UNKNOWN = "unknown";

    public static final String INTENT_ITEM_INFO = "intent_item_info";

    private static String sAppName;
    private static String sVerionName;
    public static void init(Context context){
        mAppContext = context;
        //sDataFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public static String getCacheDir() {
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            File cacheFile = mAppContext.getExternalCacheDir();
            if(null != cacheFile) {
                return cacheFile.getPath();
            }
        }
        return mAppContext.getCacheDir().getPath();
    }

    /**
     *
     * @param time 2016-10-09T11:45:38.236Z
     * @return
     */
    public static String formatPublishedTime(String time){
        if(TextUtils.isEmpty(time))
            return null;
        //time = time.replace("Z"," UTC");
        Calendar c = null;
        try {
            if(time.endsWith("Z")) {
                sDataFormatZ.parse(time);
                c = sDataFormatZ.getCalendar();
            }
            else {
                sDataFormat.parse(time);
                c = sDataFormat.getCalendar();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        if(c != null){
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;
            int day = c.get(Calendar.DAY_OF_MONTH);
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            int second = c.get(Calendar.SECOND);

            //获取当前时间
//            Date date = new Date();
//            sDataFormat.format(date);
            c.setTimeInMillis(System.currentTimeMillis());
            int cur_year = c.get(Calendar.YEAR);
            int cur_month = c.get(Calendar.MONTH) + 1;
            int cur_day = c.get(Calendar.DAY_OF_MONTH);
            int cur_hour = c.get(Calendar.HOUR_OF_DAY);
            int cur_minute = c.get(Calendar.MINUTE);
            int cur_second = c.get(Calendar.SECOND);

            if(year < cur_year){
                return (cur_year - year) + mAppContext.getResources().getString(R.string.text_years_ago);
            } else if(month < cur_month){
                return (cur_month - month) + mAppContext.getResources().getString(R.string.text_months_ago);
            } else if(day < cur_day){
                return (cur_day - day) + mAppContext.getResources().getString(R.string.text_days_ago);
            } else if(hour < cur_hour){
                return (cur_hour - hour) + mAppContext.getResources().getString(R.string.text_hours_ago);
            } else if(minute < cur_minute){
                return (cur_minute - minute) + mAppContext.getResources().getString(R.string.text_minutes_ago);
            } else if(second < cur_second){
                return (cur_second - second) + mAppContext.getResources().getString(R.string.text_seconds_ago);
            }
        }
        return null;
    }

    public static void setTextViewLeftDrawableForHeader(TextView textView, IIcon icon){
        Drawable drawable = new IconicsDrawable(mAppContext)
                .icon(icon)
                .color(mAppContext.getResources().getColor(R.color.theme_primary))
                .sizeDp(14);
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    public static void startArticleDetailActivity(Activity activity, final GankDetailData data){
        if(activity == null || data == null)
            return;
        Intent intent = new Intent(activity, ArticleDetailActivity.class);
        intent.putExtra(INTENT_ITEM_INFO,data);
        activity.startActivity(intent);
        BackgroundHandler.execute(new Runnable() {
            @Override
            public void run() {
                GankProviderHelper.getInstance().saveHistoryEntry(data);
            }
        });
    }

    public static void startBrowser(Activity activity, String url){
        if(activity == null)
            return;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.putExtra(Browser.EXTRA_APPLICATION_ID, activity.getPackageName());
        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
        intent.putExtra(Browser.EXTRA_CREATE_NEW_TAB, true);
        activity.startActivity(intent);
    }

    public static void startImageBrowserActivity(Activity activity, List<GankItem> list, int index) {
        if(activity == null) {
            return;
        }
        Intent intent = new Intent(activity, ImageBrowserActivity.class);
        intent.putExtra(ImageBrowserActivity.EXTRA_DATA, (Serializable)list);
        intent.putExtra(ImageBrowserActivity.EXTRA_INDEX, index);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }

    public static void copy(Activity activity, String str){
        if(activity == null)
            return;
        if (str != null && str != "") {
            ClipboardManager cm = (ClipboardManager) activity
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText(str);
        }
    }

    public static void share(Activity activity, String title, String url){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, activity.getString(R.string.share_page, title, url, getAppName()));
        activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.text_share)));
    }

    public static String getAppName(){
        if(sAppName == null){
            PackageManager packageManager = null;
            ApplicationInfo applicationInfo = null;
            try {
                packageManager = mAppContext.getPackageManager();
                applicationInfo = packageManager.getApplicationInfo(mAppContext.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                applicationInfo = null;
            }
            sAppName = (String) packageManager.getApplicationLabel(applicationInfo);
        }
        return sAppName;
    }

    public static String getVersionName(){
        if(sVerionName == null){
            PackageManager packageManager = mAppContext.getPackageManager();
            PackageInfo packageInfo = null;
            try {
                packageInfo = packageManager.getPackageInfo(mAppContext.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if(packageInfo != null){
                sVerionName = packageInfo.versionName;
            }
        }
        return sVerionName;
    }

    public static boolean isNetworkWorking(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == connectivityManager) {
            return false;
        }
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (null != wifiInfo && NetworkInfo.State.CONNECTED == wifiInfo.getState()
                || null != mobileInfo && NetworkInfo.State.CONNECTED == mobileInfo.getState()) {
            return true;
        }
        return false;
    }

    public static String getNetworkType(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                        return TYPE_WIFI;
                    } else {
                        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                        if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_GPRS
                                || tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_EDGE
                                || tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_CDMA
                                || tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_1xRTT
                                || tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_IDEN) {
                            return TYPE_2G;
                        } else if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE) {
                            return TYPE_4G;
                        } else {
                            return TYPE_3G;
                        }
                    }
                } else {
                    return TYPE_OFF;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TYPE_UNKNOWN;
    }
}
