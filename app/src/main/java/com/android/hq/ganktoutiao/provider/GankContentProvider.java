package com.android.hq.ganktoutiao.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by heqiang on 16-10-19.
 */
public class GankContentProvider extends SQLiteContentProvider {
    public static final String AUTHORITY = "gank";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PARAM_GROUP_BY = "groupBy";
    public static final String PARAM_LIMIT = "limit";

    public static final class HistoryProvider{
        public static final String HISTORY = GankSQLiteOpenHelper.HistoryTab.TABLE;
        public static final Uri URI_HISTORY = Uri.withAppendedPath(
                AUTHORITY_URI, HISTORY);
        public static final int MATCH_HISTORY = 0x0001;
    }

    public static final class FavouriteProvider{
        public static final String FAVOURITE = GankSQLiteOpenHelper.FavouriteTab.TABLE;
        public static final Uri URI_FAVOURITE = Uri.withAppendedPath(
                AUTHORITY_URI, FAVOURITE);
        public static final int MATCH_FAVOURITE = 0x0002;
    }

    static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        final UriMatcher matcher = URI_MATCHER;
        matcher.addURI(AUTHORITY, HistoryProvider.HISTORY, HistoryProvider.MATCH_HISTORY);
        matcher.addURI(AUTHORITY, FavouriteProvider.FAVOURITE, FavouriteProvider.MATCH_FAVOURITE);
    }

    private SQLiteOpenHelper mOpenHelper;
    @Override
    public SQLiteOpenHelper getDatabaseHelper(Context context) {
        synchronized (this) {
            if(mOpenHelper == null){
                mOpenHelper = new GankSQLiteOpenHelper(context);
            }
            return mOpenHelper;
        }
    }

    @Override
    public Uri insertInTransaction(Uri uri, ContentValues values, boolean callerIsSyncAdapter) {
        int match = URI_MATCHER.match(uri);
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long id = -1;

        String table = getMatchTable(match);
        if(null != table) {
            id = db.insert(table, null, values);
        }else{
            throw new UnsupportedOperationException("Unknown insert URI " + uri);
        }

        if(id >= 0) {
            postNotifyUri(uri);
            return ContentUris.withAppendedId(uri, id);
        }
        return null;
    }

    @Override
    public int updateInTransaction(Uri uri, ContentValues values, String selection, String[] selectionArgs, boolean callerIsSyncAdapter) {
        int match = URI_MATCHER.match(uri);
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int id = -1;

        String table = getMatchTable(match);
        if(null != table) {
            id = db.update(table, values, selection, selectionArgs);
        }else {
            throw new UnsupportedOperationException("Unknown insert URI " + uri);
        }
        if(id > 0) {
            postNotifyUri(uri);
        }
        return id;
    }

    @Override
    public int deleteInTransaction(Uri uri, String selection, String[] selectionArgs, boolean callerIsSyncAdapter) {
        int match = URI_MATCHER.match(uri);
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int id = -1;

        String table = getMatchTable(match);
        if(null != table) {
            id = db.delete(table, selection, selectionArgs);
        }else {
            throw new UnsupportedOperationException("Unknown insert URI " + uri);
        }
        if(id > 0) {
            postNotifyUri(uri);
        }
        return id;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final int match = URI_MATCHER.match(uri);

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String limit = uri.getQueryParameter(PARAM_LIMIT);
        String groupBy = uri.getQueryParameter(PARAM_GROUP_BY);
        String table = getMatchTable(match);
        if(null != table) {
            qb.setTables(table);
        }else {
            throw new UnsupportedOperationException("Unknown insert URI " + uri);
        }

        return qb.query(db, projection, selection, selectionArgs, groupBy, null, sortOrder, limit);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    private String getMatchTable(int match) {
        String table = null;
        switch (match){
            case HistoryProvider.MATCH_HISTORY:
                table = HistoryProvider.HISTORY;
                break;
            case FavouriteProvider.MATCH_FAVOURITE:
                table = FavouriteProvider.FAVOURITE;
                break;
        }
        return table;
    }
}
