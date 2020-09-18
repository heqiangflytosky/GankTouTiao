package com.android.hq.ganktoutiao.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.android.hq.ganktoutiao.data.GankDetailData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heqiang on 16-10-20.
 */
public class GankProviderHelper {
    private static GankProviderHelper sInstance;
    private Context mContext;
    private GankProviderHelper(Context context){
        mContext = context;
    }
    public static GankProviderHelper createInstance(Context context){
        if(sInstance == null){
            synchronized (GankProviderHelper.class){
                if (sInstance == null){
                    sInstance = new GankProviderHelper(context);
                }
            }
        }
        return sInstance;
    }

    public static GankProviderHelper getInstance(){
        return sInstance;
    }

    //----------------------history

    public void saveHistoryEntry(GankDetailData data){
        if(data == null){
            return;
        }

        ContentValues contentValues = GankSQLiteOpenHelper.HistoryTab.convert2ContentValue(data);
        long id = queryHistoryEntry(data.gank_id, data.gank_type);
        if(id < 0){
            mContext.getContentResolver().insert(GankContentProvider.HistoryProvider.URI_HISTORY,
                    contentValues);
        } else {
            mContext.getContentResolver().update(GankContentProvider.HistoryProvider.URI_HISTORY,
                    contentValues,
                    GankSQLiteOpenHelper.HistoryTab._ID + "=?",
                    new String[]{String.valueOf(id)});
        }
        return;
    }

    public long queryHistoryEntry(String gankID, String type){
        long id = -1;
        if(TextUtils.isEmpty(gankID) || TextUtils.isEmpty(type)){
            return id;
        }

        Cursor c = null;
        try {
            c = mContext.getContentResolver().query(GankContentProvider.HistoryProvider.URI_HISTORY,
                    new String[]{GankSQLiteOpenHelper.HistoryTab._ID},
                    GankSQLiteOpenHelper.HistoryTab.GANK_ID + "=? AND " + GankSQLiteOpenHelper.HistoryTab.GANK_TYPE + "=?",
                    new String[]{gankID,type},
                    null);
            if(c != null && c.moveToFirst()){
                id = c.getLong(0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(c != null){
                c.close();
            }
        }
        return id;
    }

    public int getHistoryCount(){
        int count = 0;
        Cursor c = null;
        try {
            c = mContext.getContentResolver().query(GankContentProvider.HistoryProvider.URI_HISTORY,
                    null,
                    null,
                    null,
                    null);
            if(c != null){
                count = c.getCount();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(c != null){
                c.close();
            }
        }
        return count;
    }

    public List<GankDetailData> queryHistories(){
        List<GankDetailData> dataList = new ArrayList<>();
        String sort = GankSQLiteOpenHelper.HistoryTab.GANK_READ_DATE + " DESC";
        Cursor c = null;
        try{
            c = mContext.getContentResolver().query(GankContentProvider.HistoryProvider.URI_HISTORY,
                    null,
                    null,
                    null,
                    sort);
            if (c != null && c.moveToFirst()) {
                do {
                    GankDetailData data = new GankDetailData(c.getString(c.getColumnIndex(GankSQLiteOpenHelper.HistoryTab.GANK_ID)),
                            c.getString(c.getColumnIndex(GankSQLiteOpenHelper.HistoryTab.GANK_TYPE)),
                            c.getString(c.getColumnIndex(GankSQLiteOpenHelper.HistoryTab.GANK_URL)),
                            c.getString(c.getColumnIndex(GankSQLiteOpenHelper.HistoryTab.GANK_WHO)),
                            c.getString(c.getColumnIndex(GankSQLiteOpenHelper.HistoryTab.GANK_TITLE)),
                            c.getString(c.getColumnIndex(GankSQLiteOpenHelper.HistoryTab.GANK_DESC)),
                            c.getString(c.getColumnIndex(GankSQLiteOpenHelper.HistoryTab.GANK_PUBLISED_DATE)),
                            Long.valueOf(c.getString(c.getColumnIndex(GankSQLiteOpenHelper.HistoryTab.GANK_READ_DATE))));
                    dataList.add(data);
                }while (c.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(c != null){
                c.close();
            }
        }
        return dataList;
    }

    //----------------------favourite

    public boolean saveFavouriteEntry(GankDetailData data){
        if(data == null){
            return false;
        }

        ContentValues contentValues = GankSQLiteOpenHelper.FavouriteTab.convert2ContentValue(data);
        long id = queryFavouriteEntry(data.gank_id, data.gank_type);
        if(id < 0){
            mContext.getContentResolver().insert(GankContentProvider.FavouriteProvider.URI_FAVOURITE,
                    contentValues);
        } else {
            mContext.getContentResolver().update(GankContentProvider.FavouriteProvider.URI_FAVOURITE,
                    contentValues,
                    GankSQLiteOpenHelper.FavouriteTab._ID + "=?",
                    new String[]{String.valueOf(id)});
        }
        return true;
    }

    public long queryFavouriteEntry(String gankID, String type){
        long id = -1;
        if(TextUtils.isEmpty(gankID) || TextUtils.isEmpty(type)){
            return id;
        }

        Cursor c = null;
        try {
            c = mContext.getContentResolver().query(GankContentProvider.FavouriteProvider.URI_FAVOURITE,
                    new String[]{GankSQLiteOpenHelper.FavouriteTab._ID},
                    GankSQLiteOpenHelper.FavouriteTab.GANK_ID + "=? AND " + GankSQLiteOpenHelper.FavouriteTab.GANK_TYPE + "=?",
                    new String[]{gankID,type},
                    null);
            if(c != null && c.moveToFirst()){
                id = c.getLong(0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(c != null){
                c.close();
            }
        }
        return id;
    }

    public boolean deleteFavouriteEntry(String gankID, String type){
        int count = mContext.getContentResolver().delete(GankContentProvider.FavouriteProvider.URI_FAVOURITE,
                GankSQLiteOpenHelper.FavouriteTab.GANK_ID + "=? AND " + GankSQLiteOpenHelper.FavouriteTab.GANK_TYPE + "=?",
                new String[]{gankID,type});
        return count > 0;
    }

    public int getFavouriteCount(){
        int count = 0;
        Cursor c = null;
        try {
            c = mContext.getContentResolver().query(GankContentProvider.FavouriteProvider.URI_FAVOURITE,
                    null,
                    null,
                    null,
                    null);
            if(c != null){
                count = c.getCount();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(c != null){
                c.close();
            }
        }
        return count;
    }

    public List<GankDetailData> queryFavourites(){
        List<GankDetailData> dataList = new ArrayList<>();
        Cursor c = null;
        String sort = GankSQLiteOpenHelper.FavouriteTab.GANK_COLLECT_DATE + " DESC";
        try{
            c = mContext.getContentResolver().query(GankContentProvider.FavouriteProvider.URI_FAVOURITE,
                    null,
                    null,
                    null,
                    sort);
            if (c != null && c.moveToFirst()) {
                do {
                    GankDetailData data = new GankDetailData(c.getString(c.getColumnIndex(GankSQLiteOpenHelper.FavouriteTab.GANK_ID)),
                            c.getString(c.getColumnIndex(GankSQLiteOpenHelper.FavouriteTab.GANK_TYPE)),
                            c.getString(c.getColumnIndex(GankSQLiteOpenHelper.FavouriteTab.GANK_URL)),
                            c.getString(c.getColumnIndex(GankSQLiteOpenHelper.FavouriteTab.GANK_WHO)),
                            c.getString(c.getColumnIndex(GankSQLiteOpenHelper.FavouriteTab.GANK_TITLE)),
                            c.getString(c.getColumnIndex(GankSQLiteOpenHelper.FavouriteTab.GANK_DESC)),
                            c.getString(c.getColumnIndex(GankSQLiteOpenHelper.FavouriteTab.GANK_PUBLISED_DATE)),
                            Long.valueOf(c.getString(c.getColumnIndex(GankSQLiteOpenHelper.FavouriteTab.GANK_COLLECT_DATE))));
                    dataList.add(data);
                }while (c.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(c != null){
                c.close();
            }
        }
        return dataList;
    }
}
