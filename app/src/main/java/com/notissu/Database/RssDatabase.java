package com.notissu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.notissu.Model.RssItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by forhack on 2016-12-04.
 */
public class RssDatabase extends SQLiteOpenHelper {
    /** Schema version. */
    public static final int DATABASE_VERSION = 8;
    /** Filename for SQLite file. */
    public static final String DATABASE_NAME = "MainNotice.db";

    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String COMMA_SEP = ",";

    //main_notice Table 생성 SQL 코드
    private static final String SQL_CREATE_MAIN_NOTICE =
            "CREATE TABLE " +
                    RssItem.MainNotice.TABLE_NAME + " (" +
                    RssItem.MainNotice.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    RssItem.MainNotice.COLUMN_NAME_GUID + TYPE_TEXT + COMMA_SEP +
                    RssItem.MainNotice.COLUMN_NAME_TITLE + TYPE_TEXT + COMMA_SEP +
                    RssItem.MainNotice.COLUMN_NAME_LINK + TYPE_TEXT + COMMA_SEP +
                    RssItem.MainNotice.COLUMN_NAME_PUBLISH_DATE + TYPE_INTEGER + COMMA_SEP +
                    RssItem.MainNotice.COLUMN_NAME_DESCRIPTION + TYPE_TEXT + COMMA_SEP +
                    RssItem.MainNotice.COLUMN_NAME_IS_READ + TYPE_INTEGER + COMMA_SEP +
                    RssItem.MainNotice.COLUMN_NAME_CATEGORY + TYPE_TEXT + ")";

    //main_notice Table 삭제 SQL 코드
    private static final String SQL_DELETE_MAIN_NOTICE =
            "DROP TABLE IF EXISTS " + RssItem.MainNotice.TABLE_NAME;

    //main_notice Table 생성 SQL 코드
    private static final String SQL_CREATE_LIBRARY_NOTICE =
            "CREATE TABLE " +
                    RssItem.LibraryNotice.TABLE_NAME + " (" +
                    RssItem.LibraryNotice.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    RssItem.LibraryNotice.COLUMN_NAME_GUID + TYPE_TEXT + COMMA_SEP +
                    RssItem.LibraryNotice.COLUMN_NAME_TITLE + TYPE_TEXT + COMMA_SEP +
                    RssItem.LibraryNotice.COLUMN_NAME_LINK + TYPE_TEXT + COMMA_SEP +
                    RssItem.LibraryNotice.COLUMN_NAME_PUBLISH_DATE + TYPE_INTEGER + COMMA_SEP +
                    RssItem.LibraryNotice.COLUMN_NAME_DESCRIPTION + TYPE_TEXT + COMMA_SEP +
                    RssItem.LibraryNotice.COLUMN_NAME_IS_READ + TYPE_INTEGER + ")";

    //main_notice Table 삭제 SQL 코드
    private static final String SQL_DELETE_LIBRARY_NOTICE =
            "DROP TABLE IF EXISTS " + RssItem.LibraryNotice.TABLE_NAME;

    //main_notice Table 생성 SQL 코드
    private static final String SQL_CREATE_STARRED =
            "CREATE TABLE " +
                    RssItem.Starred.TABLE_NAME + " (" +
                    RssItem.Starred.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    RssItem.Starred.COLUMN_NAME_TITLE + TYPE_TEXT + ")";

    //main_notice Table 삭제 SQL 코드
    private static final String SQL_DELETE_STARRED =
            "DROP TABLE IF EXISTS " + RssItem.Starred.TABLE_NAME;

    //main_notice Table 생성 SQL 코드
    private static final String SQL_CREATE_KEYWORD =
            "CREATE TABLE " +
                    RssItem.Keyword.TABLE_NAME + " (" +
                    RssItem.Keyword.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    RssItem.Keyword.COLUMN_NAME_KEYWORD + TYPE_TEXT + ")";

    //main_notice Table 삭제 SQL 코드
    private static final String SQL_DELETE_KEYWORD =
            "DROP TABLE IF EXISTS " + RssItem.Keyword.TABLE_NAME;

    private SQLiteDatabase readDatabase;
    private SQLiteDatabase writeDatabase;

    private static RssDatabase instance = null;

    public static void setInstance(Context context) {
        instance = new RssDatabase(context);
    }

    public static RssDatabase getInstance() {
        return instance;
    }

    public SQLiteDatabase getReadDatabase() {
        return readDatabase;
    }

    public SQLiteDatabase getWriteDatabase() {
        return writeDatabase;
    }

    private RssDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        readDatabase = this.getReadableDatabase();
        writeDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MAIN_NOTICE);
        db.execSQL(SQL_CREATE_LIBRARY_NOTICE);
        db.execSQL(SQL_CREATE_STARRED);
        db.execSQL(SQL_CREATE_KEYWORD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_MAIN_NOTICE);
        db.execSQL(SQL_DELETE_LIBRARY_NOTICE);
        db.execSQL(SQL_DELETE_STARRED);
        db.execSQL(SQL_DELETE_KEYWORD);
        onCreate(db);
    }
}
