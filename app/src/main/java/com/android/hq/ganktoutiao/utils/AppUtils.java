package com.android.hq.ganktoutiao.utils;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.Browser;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.android.hq.ganktoutiao.R;
import com.android.hq.ganktoutiao.data.GankDetailData;
import com.android.hq.ganktoutiao.data.GankItem;
import com.android.hq.ganktoutiao.data.bean.GankItemBean;
import com.android.hq.ganktoutiao.ui.activity.ArticleDetailActivity;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by heqiang on 16-10-8.
 */
public class AppUtils {
    private static Context mAppContext;
    private static SimpleDateFormat sDataFormatZ = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static SimpleDateFormat sDataFormat6S = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

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
                sDataFormat6S.parse(time);
                c = sDataFormat6S.getCalendar();
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

    public static void startArticleDetailActivity(Activity activity, GankDetailData data){
        if(activity == null || data == null)
            return;
        Intent intent = new Intent(activity, ArticleDetailActivity.class);
        intent.putExtra(INTENT_ITEM_INFO,data);
        activity.startActivity(intent);
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
}
