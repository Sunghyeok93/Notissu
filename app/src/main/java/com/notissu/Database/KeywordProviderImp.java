package com.notissu.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.notissu.Model.RssItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by forhack on 2016-12-19.
 */
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

public class KeywordProviderImp implements KeywordProvider {
    private LowDBProvider mLowDBProvider;
    private SQLiteDatabase mWriteDatabase;

    public KeywordProviderImp() {
        mWriteDatabase = RssDatabase.getInstance().getWriteDatabase();
        mLowDBProvider = new LowDBProviderImp();
    }

    //지정 Keyword의 item들 가져오기
    @Override
    public List<RssItem> getKeyword(String keyword) {
        String selection = RssItem.Common.COLUMN_NAME_TITLE + " LIKE ?";
        String selectionarg = "%"+keyword+"%";
        return mLowDBProvider.getNotice(selection, new String[]{selectionarg});

    }

    //Keyword 목록 가져오기
    @Override
    public List<String> getKeyword() {
        List<String> keywordList = new ArrayList<>();
        Cursor results = mLowDBProvider.getCursor(RssItem.Keyword.TABLE_NAME,null,null);

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
    @Override
    public long addKeyword(String keyword) {
        ContentValues values = new ContentValues();

        values.put(RssItem.Keyword.COLUMN_NAME_KEYWORD,keyword);
        return mWriteDatabase.insert(RssItem.Keyword.TABLE_NAME,null,values);
    }

    //인자로 넣은 GUID와 일치하는 RSS 삭제
    //일치하는 row가 없으면 0 반환
    @Override
    public int deleteKeyword(String keyword) {
        //두번째 인자를 null로 채우면 모든 row 삭제
        return mWriteDatabase.delete(RssItem.Keyword.TABLE_NAME,
                RssItem.Keyword.COLUMN_NAME_KEYWORD+"=?",new String[]{keyword});
    }

    @Override
    public int getNotReadCount(String keyword) {
        String selection = RssItem.Common.COLUMN_NAME_TITLE + " LIKE ? AND " + RssItem.Common.COLUMN_NAME_IS_READ + "=?";
        String[] strings = new String[2];
        strings[0] = "%"+keyword+"%"; // 키워드드가 같고
        strings[1] = RssItem.NOT_READ+""; // 안 읽은거
        List<RssItem> rssItemList = mLowDBProvider.getNotice(selection, strings);
        return rssItemList.size();
    }
}
