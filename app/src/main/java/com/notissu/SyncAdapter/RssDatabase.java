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
    public static final int DATABASE_VERSION = 7;
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
                    RssItem.LibraryNotice.COLUMN_NAME_DESCRIPTION + TYPE_TEXT + ")";

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


    //DB에 있는 모든 RSS를 List형태로 반환하는 메소드
    public Cursor getCursor(String table, String selection, String[] selectionargs) {
        String orderBy = null;
        if (table == RssItem.MainNotice.TABLE_NAME || table == RssItem.LibraryNotice.TABLE_NAME) {
            orderBy = RssItem.Common.COLUMN_NAME_PUBLISH_DATE + " DESC ";
        }
        Cursor results = readDatabase.query(table, null, selection, selectionargs, null, null, orderBy);
        results.moveToFirst();
        return results;
    }

    // guid를 입력해서 Rssitem을 만드는 메소드
    public List<RssItem> getNotice(String selection, String[] selectionargs) {
        List<RssItem> rssItemList = new ArrayList<>();
        String query = "select "+
                RssItem.MainNotice.COLUMN_NAME_ID+","+
                RssItem.MainNotice.COLUMN_NAME_GUID+","+
                RssItem.MainNotice.COLUMN_NAME_TITLE+","+
                RssItem.MainNotice.COLUMN_NAME_LINK+","+
                RssItem.MainNotice.COLUMN_NAME_PUBLISH_DATE+","+
                RssItem.MainNotice.COLUMN_NAME_DESCRIPTION+
                " from "+
                RssItem.MainNotice.TABLE_NAME+
                " where "+
                selection +
                " union all " +
                " select " +
                RssItem.LibraryNotice.COLUMN_NAME_ID+","+
                RssItem.LibraryNotice.COLUMN_NAME_GUID+","+
                RssItem.LibraryNotice.COLUMN_NAME_TITLE+","+
                RssItem.LibraryNotice.COLUMN_NAME_LINK+","+
                RssItem.LibraryNotice.COLUMN_NAME_PUBLISH_DATE+","+
                RssItem.LibraryNotice.COLUMN_NAME_DESCRIPTION+
                " from "+
                RssItem.LibraryNotice.TABLE_NAME+
                " where "+
                selection;

        Cursor results = readDatabase.rawQuery(query,selectionargs);
        results.moveToFirst();

        while (!results.isAfterLast()){
            RssItem rssItem = new RssItem(results);
            rssItemList.add(rssItem);
            results.moveToNext();
        };
        results.close();
        return rssItemList;
    }

    /*Main 공지사항들의 기능들
    * Main테이블을 카테고리별로 골라 가지고 옴
    * List<RssItem> getMainNotice(String category)
    * 쓰기
    * long addMainNotice(RssItem rssItem)
    * 수정하기
    * int updateMainNotice(RssItem rssItem)
    * 삭제하기.
    * int deleteMainNotice(RssItem rssItem)
    * */

    //Main 공지사항 가져오기
    public List<RssItem> getMainNotice(String category) {
        List<RssItem> rssItemList = new ArrayList<>();
        Cursor results = null;
        if (category == NoticeProvider.NOTICE_SSU_ALL) {
            results = getCursor(RssItem.MainNotice.TABLE_NAME,null,null);
        } else {
            results = getCursor(RssItem.MainNotice.TABLE_NAME,
                    RssItem.MainNotice.COLUMN_NAME_CATEGORY+"=?",new String[]{category});
        }

        while (!results.isAfterLast()){
            RssItem rssItem = new RssItem(results);
            rssItemList.add(rssItem);
            results.moveToNext();
        };
        results.close();
        return rssItemList;
    }

    //입력받은 RSS를 DB에 삽입하는 메소드
    //실패했을 때 -1 반환
    public long addMainNotice(RssItem isExist) {
        ContentValues values = new ContentValues();
        values.put(RssItem.MainNotice.COLUMN_NAME_GUID,isExist.getGuid());
        values.put(RssItem.MainNotice.COLUMN_NAME_TITLE,isExist.getTitle());
        values.put(RssItem.MainNotice.COLUMN_NAME_LINK,isExist.getLink());
        values.put(RssItem.MainNotice.COLUMN_NAME_PUBLISH_DATE,isExist.getPublishDate());
        values.put(RssItem.MainNotice.COLUMN_NAME_DESCRIPTION,isExist.getDescription());
        values.put(RssItem.MainNotice.COLUMN_NAME_CATEGORY,isExist.getCategory());

        return writeDatabase.insert(RssItem.MainNotice.TABLE_NAME,null,values);
    }

    //입력받은 RSS를 DB에 업데이트(수정)하는 메소드
    //일치하는 row가 없으면 0 반환
    public int updateMainNotice(RssItem isExist) {
        ContentValues values = new ContentValues();
        values.put(RssItem.MainNotice.COLUMN_NAME_TITLE,isExist.getTitle());
        values.put(RssItem.MainNotice.COLUMN_NAME_LINK,isExist.getLink());
        values.put(RssItem.MainNotice.COLUMN_NAME_PUBLISH_DATE,isExist.getPublishDate());
        values.put(RssItem.MainNotice.COLUMN_NAME_DESCRIPTION,isExist.getDescription());
        values.put(RssItem.MainNotice.COLUMN_NAME_CATEGORY,isExist.getCategory());

        return writeDatabase.update(RssItem.MainNotice.TABLE_NAME,values,
                RssItem.MainNotice.COLUMN_NAME_GUID+"=?",new String[]{isExist.getGuid()});
    }

    //인자로 넣은 GUID와 일치하는 RSS 삭제
    //일치하는 row가 없으면 0 반환
    public int deleteMainNotice(String guid) {
        //두번째 인자를 null로 채우면 모든 row 삭제
        return writeDatabase.delete(RssItem.MainNotice.TABLE_NAME,
                RssItem.MainNotice.COLUMN_NAME_GUID+"=?",new String[]{guid});
    }

    /*Library 공지사항들의 기능들
    * Library 테이블을 불러오기
    List<RssItem> getLibraryNotice()
    * 쓰기
    long addLibrary(RssItem rssItem)
    * 수정하기
    int updateLibrary(RssItem rssItem)
    * 삭제하기.
    int deleteLibrary(RssItem rssItem)
    * */

    //Library 공지사항 가져오기
    public List<RssItem> getLibraryNotice() {
        List<RssItem> rssItemList = new ArrayList<>();
        Cursor results = getCursor(RssItem.LibraryNotice.TABLE_NAME,null,null);

        while (!results.isAfterLast()){
            RssItem rssItem = new RssItem(results);
            rssItemList.add(rssItem);
            results.moveToNext();
        };
        results.close();
        return rssItemList;
    }

    //입력받은 RSS를 DB에 삽입하는 메소드
    //실패했을 때 -1 반환
    public long addLibraryNotice(RssItem isExist) {
        ContentValues values = new ContentValues();

        values.put(RssItem.LibraryNotice.COLUMN_NAME_GUID,isExist.getGuid());
        values.put(RssItem.LibraryNotice.COLUMN_NAME_TITLE,isExist.getTitle());
        values.put(RssItem.LibraryNotice.COLUMN_NAME_LINK,isExist.getLink());
        values.put(RssItem.LibraryNotice.COLUMN_NAME_PUBLISH_DATE,isExist.getPublishDate());
        values.put(RssItem.LibraryNotice.COLUMN_NAME_DESCRIPTION,isExist.getDescription());

        return writeDatabase.insert(RssItem.LibraryNotice.TABLE_NAME,null,values);
    }

    //입력받은 RSS를 DB에 업데이트(수정)하는 메소드
    //일치하는 row가 없으면 0 반환
    public int updateLibraryNotice(RssItem isExist) {
        ContentValues values = new ContentValues();
        values.put(RssItem.LibraryNotice.COLUMN_NAME_TITLE,isExist.getTitle());
        values.put(RssItem.LibraryNotice.COLUMN_NAME_LINK,isExist.getLink());
        values.put(RssItem.LibraryNotice.COLUMN_NAME_PUBLISH_DATE,isExist.getPublishDate());
        values.put(RssItem.LibraryNotice.COLUMN_NAME_DESCRIPTION,isExist.getDescription());

        return writeDatabase.update(RssItem.LibraryNotice.TABLE_NAME,values,
                RssItem.LibraryNotice.COLUMN_NAME_GUID+"=?",new String[]{isExist.getGuid()});
    }

    //인자로 넣은 GUID와 일치하는 RSS 삭제
    //일치하는 row가 없으면 0 반환
    public int deleteLibraryNotice(String guid) {
        //두번째 인자를 null로 채우면 모든 row 삭제
        return writeDatabase.delete(RssItem.LibraryNotice.TABLE_NAME,
                RssItem.LibraryNotice.COLUMN_NAME_GUID+"=?",new String[]{guid});
    }

    /*Starred 기능들
    * Starred 가져오기
    List<RssItem> getStarred()
    * 쓰기
    long addStarred(RssItem rssItem)
    * 삭제하기.
    int deleteStarred(RssItem rssItem)
    * */

    //Starred 가져오기
    public List<RssItem> getStarred() {
        List<RssItem> rssItemList = new ArrayList<>();
        Cursor results = getCursor(RssItem.Starred.TABLE_NAME,null,null);

        while (!results.isAfterLast()){
            int index = results.getColumnIndex(RssItem.Starred.COLUMN_NAME_TITLE);
            String title = results.getString(index);
            List<RssItem> rssItem = getNotice(RssItem.Common.COLUMN_NAME_TITLE+"=?", new String[]{title});
            // Starred가 여러개 나올 가능성은 배제하고 무조건 첫번째 것만 취한다.
            // 코드에 버그가 없다면 1개는 꼭 나온다.
            if (rssItem.size() > 0) {
                rssItemList.add(rssItem.get(0));
            }
            results.moveToNext();
        };
        results.close();
        return rssItemList;
    }
    
    //입력받은 RSS를 DB에 삽입하는 메소드
    //실패했을 때 -1 반환
    public long addStarred(String noticeTitle) {
        ContentValues values = new ContentValues();

        values.put(RssItem.Starred.COLUMN_NAME_TITLE,noticeTitle);

        return writeDatabase.insert(RssItem.Starred.TABLE_NAME,null,values);
    }

    //인자로 넣은 GUID와 일치하는 RSS 삭제
    //일치하는 row가 없으면 0 반환
    public int deleteStarred(String noticeTitle) {
        //두번째 인자를 null로 채우면 모든 row 삭제
        return writeDatabase.delete(RssItem.Starred.TABLE_NAME,
                RssItem.Starred.COLUMN_NAME_TITLE +"=?",new String[]{noticeTitle});
    }

    /*Keyword 기능들
    * 지정 Keyword의 item들 가져오기
    List<RssItem> getKeywordData(String keyword)
    * Keyword 목록 가져오기
    List<String> getKeywordData()
    * 쓰기
    long addKeyword(String keyword)
    * 삭제하기.
    int deleteKeyword(String keyword)
    * */

    //지정 Keyword의 item들 가져오기
    public List<RssItem> getKeyword(String keyword) {
        String selection = RssItem.Common.COLUMN_NAME_TITLE + " LIKE ?";
        String selectionarg = "%"+keyword+"%";
        return getNotice(selection, new String[]{selectionarg});
    }

    //Keyword 목록 가져오기
    public List<String> getKeyword() {
        List<String> keywordList = new ArrayList<>();
        Cursor results = getCursor(RssItem.Keyword.TABLE_NAME,null,null);

        while (!results.isAfterLast()){
            int index = results.getColumnIndex(RssItem.Keyword.COLUMN_NAME_KEYWORD);
            String keyword = results.getString(index);
            keywordList.add(keyword);
            results.moveToNext();
        };

        results.close();
        return keywordList;
    }

    //입력받은 RSS를 DB에 삽입하는 메소드
    //실패했을 때 -1 반환
    public long addKeyword(String keyword) {
        ContentValues values = new ContentValues();

        values.put(RssItem.Keyword.COLUMN_NAME_KEYWORD,keyword);

        return writeDatabase.insert(RssItem.Keyword.TABLE_NAME,null,values);
    }

    //인자로 넣은 GUID와 일치하는 RSS 삭제
    //일치하는 row가 없으면 0 반환
    public int deleteKeyword(String keyword) {
        //두번째 인자를 null로 채우면 모든 row 삭제
        return writeDatabase.delete(RssItem.Keyword.TABLE_NAME,
                RssItem.Keyword.COLUMN_NAME_KEYWORD+"=?",new String[]{keyword});
    }

}
