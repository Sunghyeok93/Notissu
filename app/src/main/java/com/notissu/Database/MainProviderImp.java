package com.notissu.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.notissu.Model.RssItem;

import java.util.ArrayList;
import java.util.List;

import static com.notissu.Model.RssItem.MainNotice.TABLE_NAME;

/**
 * Created by forhack on 2016-12-19.
 */
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
public class MainProviderImp implements MainProvider {
    private LowDBProvider mLowDBProvider;
    private SQLiteDatabase mWriteDatabase;

    public MainProviderImp() {
        mWriteDatabase = RssDatabase.getInstance().getWriteDatabase();
        mLowDBProvider = new LowDBProviderImp();
    }

    @Override
    public List<RssItem> getSsuNotice(String category) {
        switch (category) {
            case NOTICE_SSU_ALL:
                return getNotice(category);
            case NOTICE_SSU_HACKSA:
                return getNotice(category);
            case NOTICE_SSU_JANGHACK:
                return getNotice(category);
            case NOTICE_SSU_KUCKJE:
                return getNotice(category);
            case NOTICE_SSU_WAEKUCK:
                return getNotice(category);
            case NOTICE_SSU_MOJIP:
                return getNotice(category);
            case NOTICE_SSU_KYONE:
                return getNotice(category);
            case NOTICE_SSU_KYOWAE:
                return getNotice(category);
            case NOTICE_SSU_BONGSA:
                return getNotice(category);
            default:
                return getNotice(NOTICE_SSU_ALL);
        }
    }

    //Main 공지사항 가져오기
    @Override
    public List<RssItem> getNotice(String category) {
        List<RssItem> rssItemList = new ArrayList<>();
        Cursor results = null;
        if (category == NOTICE_SSU_ALL) {
            results = mLowDBProvider.getCursor(TABLE_NAME,null,null);
        } else {
            results = mLowDBProvider.getCursor(TABLE_NAME,
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
    @Override
    public long addNotice(RssItem isExist) {
        ContentValues values = new ContentValues();
        values.put(RssItem.MainNotice.COLUMN_NAME_GUID,isExist.getGuid());
        values.put(RssItem.MainNotice.COLUMN_NAME_TITLE,isExist.getTitle());
        values.put(RssItem.MainNotice.COLUMN_NAME_LINK,isExist.getLink());
        values.put(RssItem.MainNotice.COLUMN_NAME_PUBLISH_DATE,isExist.getPublishDate());
        values.put(RssItem.MainNotice.COLUMN_NAME_DESCRIPTION,isExist.getDescription());
        values.put(RssItem.MainNotice.COLUMN_NAME_CATEGORY,isExist.getCategory());
        values.put(RssItem.MainNotice.COLUMN_NAME_IS_READ,isExist.getIsRead());

        return mWriteDatabase.insert(TABLE_NAME,null,values);
    }

    //입력받은 RSS를 DB에 업데이트(수정)하는 메소드
    //일치하는 row가 없으면 0 반환
    @Override
    public int updateNotice(RssItem isExist) {
        ContentValues values = new ContentValues();
        values.put(RssItem.MainNotice.COLUMN_NAME_GUID,isExist.getGuid());
        values.put(RssItem.MainNotice.COLUMN_NAME_LINK,isExist.getLink());
        values.put(RssItem.MainNotice.COLUMN_NAME_PUBLISH_DATE,isExist.getPublishDate());
        values.put(RssItem.MainNotice.COLUMN_NAME_DESCRIPTION,isExist.getDescription());
        values.put(RssItem.MainNotice.COLUMN_NAME_CATEGORY,isExist.getCategory());
        values.put(RssItem.MainNotice.COLUMN_NAME_IS_READ,isExist.getIsRead());

        return mWriteDatabase.update(TABLE_NAME,values,
                RssItem.MainNotice.COLUMN_NAME_TITLE+"=?",new String[]{isExist.getTitle()});
    }

    //인자로 넣은 GUID와 일치하는 RSS 삭제
    //일치하는 row가 없으면 0 반환
    @Override
    public int deleteNotice(String title) {
        //두번째 인자를 null로 채우면 모든 row 삭제
        return mWriteDatabase.delete(TABLE_NAME,
                RssItem.MainNotice.COLUMN_NAME_TITLE+"=?",new String[]{title});
    }

    //안 읽은 공지사항의 개수 반환
    @Override
    public int getNotReadCount() {
        int count = 0;
        List<RssItem> rssList = getNotice(NOTICE_SSU_ALL);;
        
        for (RssItem rss : rssList) {
            if (rss.getIsRead() == RssItem.NOT_READ) {
                count++;
            }
        }
        return count;
    }

    //안읽은 공지사항 모두 업데이트 하도록 한다.
    @Override
    public int updateAllReadCount() {
        ContentValues values = new ContentValues();
        values.put(RssItem.Common.COLUMN_NAME_IS_READ,RssItem.READ);
        return mWriteDatabase.update(TABLE_NAME,values,null,null);
    }
    
}
