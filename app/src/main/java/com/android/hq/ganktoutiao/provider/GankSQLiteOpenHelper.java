package com.android.hq.ganktoutiao.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.hq.ganktoutiao.data.GankDetailData;

/**
 * Created by heqiang on 16-10-20.
 */
public class GankSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "GankSQLiteOpenHelper";
    private static final String DB_NAME = "gank.db";
    private static final int DB_VERSION = 2;

    public static class UserData{
        public static final String _ID = "_id";
        public static final String GANK_ID = "gank_id";
        public static final String GANK_TYPE = "type";
        public static final String GANK_WHO = "who";
        public static final String GANK_PUBLISED_DATE = "published_date";
        public static final String GANK_URL = "url";
        public static final String GANK_TITLE = "title";
        public static final String GANK_DESC = "description";
    }

    public static final class HistoryTab extends UserData{
        public static final String TABLE = "history";

        public static final String GANK_READ_DATE = "read_date";

        public static ContentValues convert2ContentValue(GankDetailData data){
            ContentValues contentValues = new ContentValues();
            contentValues.put(GANK_ID, data.gank_id);
            contentValues.put(GANK_TYPE, data.gank_type);
            contentValues.put(GANK_WHO, data.who);
            contentValues.put(GANK_PUBLISED_DATE, data.published_date);
            contentValues.put(GANK_URL, data.url);
            contentValues.put(GANK_READ_DATE, data.action_date);
            contentValues.put(GANK_TITLE, data.title);
            contentValues.put(GANK_DESC, data.desc);
            return contentValues;
        }
    }

    public static final class FavouriteTab extends UserData{
        public static final String TABLE = "favourite";

        public static final String GANK_COLLECT_DATE = "collect_date";

        public static ContentValues convert2ContentValue(GankDetailData data){
            ContentValues contentValues = new ContentValues();
            contentValues.put(GANK_ID, data.gank_id);
            contentValues.put(GANK_TYPE, data.gank_type);
            contentValues.put(GANK_WHO, data.who);
            contentValues.put(GANK_PUBLISED_DATE, data.published_date);
            contentValues.put(GANK_URL, data.url);
            contentValues.put(GANK_COLLECT_DATE, data.action_date);
            contentValues.put(GANK_TITLE, data.title);
            contentValues.put(GANK_DESC, data.desc);
            return contentValues;
        }
    }

    public static final class SQLS{
        public static final String CREATE_HISTORY_TAB =
                "CREATE TABLE " + HistoryTab.TABLE
                        + "("
                        + HistoryTab._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + HistoryTab.GANK_ID + " TEXT,"
                        + HistoryTab.GANK_TYPE + " TEXT,"
                        + HistoryTab.GANK_WHO + " TEXT,"
                        + HistoryTab.GANK_PUBLISED_DATE +  " TEXT,"
                        + HistoryTab.GANK_URL + " TEXT,"
                        + HistoryTab.GANK_TITLE + " TEXT,"
                        + HistoryTab.GANK_DESC + " TEXT,"
                        + HistoryTab.GANK_READ_DATE + " INTEGER"
                        + ");";
        public static final String CREATE_FAVOURITE_TAB =
                "CREATE TABLE " + FavouriteTab.TABLE
                        + "("
                        + FavouriteTab._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + FavouriteTab.GANK_ID + " TEXT,"
                        + FavouriteTab.GANK_TYPE + " TEXT,"
                        + FavouriteTab.GANK_WHO + " TEXT,"
                        + FavouriteTab.GANK_PUBLISED_DATE +  " TEXT,"
                        + FavouriteTab.GANK_URL + " TEXT,"
                        + HistoryTab.GANK_TITLE + " TEXT,"
                        + HistoryTab.GANK_DESC + " TEXT,"
                        + FavouriteTab.GANK_COLLECT_DATE + " INTEGER"
                        + ");";
    }

    public GankSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTables(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(oldVersion < 2){
            upgradeTo2(sqLiteDatabase);
        }
//        if(oldVersion < 3){
//        }
    }

    private void createTables(SQLiteDatabase db) {
        db.execSQL(SQLS.CREATE_HISTORY_TAB);
        db.execSQL(SQLS.CREATE_FAVOURITE_TAB);
    }

    private void upgradeTo2(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE " + HistoryTab.TABLE + " ADD COLUMN " + HistoryTab.GANK_DESC
                + " TEXT");
        db.execSQL("ALTER TABLE " + FavouriteTab.TABLE + " ADD COLUMN " + FavouriteTab.GANK_DESC
                + " TEXT");
    }
}
