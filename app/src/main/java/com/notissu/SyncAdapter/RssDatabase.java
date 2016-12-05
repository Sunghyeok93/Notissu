package com.notissu.SyncAdapter;

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
public class RssDatabase extends SQLiteOpenHelper{
    /** Schema version. */
    public static final int DATABASE_VERSION = 2;
    /** Filename for SQLite file. */
    public static final String DATABASE_NAME = "Rss.db";

    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String COMMA_SEP = ",";


    //Table 생성 SQL 코드
    private static final String SQL_CREATE_RSS =
            "CREATE TABLE " +
                    RssItem.Rss.TABLE_NAME + " (" +
                    RssItem.Rss.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    RssItem.Rss.COLUMN_NAME_GUID + TYPE_TEXT + COMMA_SEP +
                    RssItem.Rss.COLUMN_NAME_TITLE + TYPE_TEXT + COMMA_SEP +
                    RssItem.Rss.COLUMN_NAME_LINK + TYPE_TEXT + COMMA_SEP +
                    RssItem.Rss.COLUMN_NAME_PUBLISH_DATE + TYPE_TEXT + COMMA_SEP +
                    RssItem.Rss.COLUMN_NAME_DESCRIPTION + TYPE_TEXT + ")";

    //Table 삭제 SQL 코드
    private static final String SQL_DELETE_RSS =
            "DROP TABLE IF EXISTS " + RssItem.Rss.TABLE_NAME;

    public RssDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_RSS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_RSS);
        onCreate(db);
    }


    //DB에 있는 모든 RSS를 List형태로 반환하는 메소드
    public List<RssItem> getRssItemList() {
        SQLiteDatabase readDatabase = this.getReadableDatabase();
        List<RssItem> rssItemList = new ArrayList<>();

        Cursor results = readDatabase.query(RssItem.Rss.TABLE_NAME, null, null, null, null, null, null, null);
        results.moveToFirst();

        while (!results.isAfterLast()){
            RssItem rssItem = new RssItem(results);
            rssItemList.add(rssItem);
            results.moveToNext();
        };

        return rssItemList;
    }

    //입력받은 RSS를 DB에 업데이트(수정)하는 메소드
    //일치하는 row가 없으면 0 반환
    public int updateRss(RssItem isExist) {
        SQLiteDatabase writeDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RssItem.Rss.COLUMN_NAME_TITLE,isExist.getTitle());
        values.put(RssItem.Rss.COLUMN_NAME_LINK,isExist.getLink());
        values.put(RssItem.Rss.COLUMN_NAME_PUBLISH_DATE,isExist.getPublishDate());
        values.put(RssItem.Rss.COLUMN_NAME_DESCRIPTION,isExist.getDescription());

        return writeDatabase.update(RssItem.Rss.TABLE_NAME,values,
                RssItem.Rss.COLUMN_NAME_GUID+"=?",new String[]{isExist.getGuid()});
    }

    //입력받은 RSS를 DB에 삽입하는 메소드
    //실패했을 때 -1 반환
    public long addRss(RssItem isExist) {
        SQLiteDatabase writeDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(RssItem.Rss.COLUMN_NAME_GUID,isExist.getGuid());
        values.put(RssItem.Rss.COLUMN_NAME_TITLE,isExist.getTitle());
        values.put(RssItem.Rss.COLUMN_NAME_LINK,isExist.getLink());
        values.put(RssItem.Rss.COLUMN_NAME_PUBLISH_DATE,isExist.getPublishDate());
        values.put(RssItem.Rss.COLUMN_NAME_DESCRIPTION,isExist.getDescription());

        return writeDatabase.insert(RssItem.Rss.TABLE_NAME,null,values);
    }

    //인자로 넣은 GUID와 일치하는 RSS 삭제
    //일치하는 row가 없으면 0 반환
    public int deleteRss(String guid) {
        SQLiteDatabase writeDatabase = this.getWritableDatabase();
        //두번째 인자를 null로 채우면 모든 row 삭제
        return writeDatabase.delete(RssItem.Rss.TABLE_NAME,
                RssItem.Rss.COLUMN_NAME_GUID+"=?",new String[]{guid});
    }
}
