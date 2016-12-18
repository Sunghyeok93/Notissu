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
    /*Starred 기능들
    * Starred 가져오기
    List<RssItem> getStarred()
    * 쓰기
    long addStarred(RssItem rssItem)
    * 삭제하기.
    int deleteStarred(RssItem rssItem)
    * */
public class StarredProviderImp implements StarredProvider {
    private LowDBProvider mLowDBProvider;
    private SQLiteDatabase mWriteDatabase;

    public StarredProviderImp() {
        mWriteDatabase = RssDatabase.getInstance().getWriteDatabase();
        mLowDBProvider = new LowDBProviderImp();
    }

    //Starred 가져오기
    @Override
    public List<RssItem> getStarred() {
        List<RssItem> rssItemList = new ArrayList<>();
        Cursor results = mLowDBProvider.getCursor(RssItem.Starred.TABLE_NAME,null,null);

        while (!results.isAfterLast()){
            int index = results.getColumnIndex(RssItem.Starred.COLUMN_NAME_TITLE);
            String title = results.getString(index);
            List<RssItem> rssItem = mLowDBProvider.getNotice(RssItem.Common.COLUMN_NAME_TITLE+"=?", new String[]{title});
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
    @Override
    public long addStarred(String noticeTitle) {
        ContentValues values = new ContentValues();

        values.put(RssItem.Starred.COLUMN_NAME_TITLE,noticeTitle);

        return mWriteDatabase.insert(RssItem.Starred.TABLE_NAME,null,values);
    }

    //인자로 넣은 GUID와 일치하는 RSS 삭제
    //일치하는 row가 없으면 0 반환
    @Override
    public int deleteStarred(String noticeTitle) {
        //두번째 인자를 null로 채우면 모든 row 삭제
        return mWriteDatabase.delete(RssItem.Starred.TABLE_NAME,
                RssItem.Starred.COLUMN_NAME_TITLE +"=?",new String[]{noticeTitle});
    }
}
