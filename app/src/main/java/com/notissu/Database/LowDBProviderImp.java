package com.notissu.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.notissu.Model.RssItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by forhack on 2016-12-19.
 */

public class LowDBProviderImp implements LowDBProvider {
    private SQLiteDatabase readDatabase;

    public LowDBProviderImp() {
        readDatabase = RssDatabase.getInstance().getReadDatabase();
    }

    //DB에 있는 모든 RSS를 List형태로 반환하는 메소드
    @Override
    public Cursor getCursor(String table, String selection, String[] selectionargs) {
        String orderBy = null;
        if (table == RssItem.MainNotice.TABLE_NAME || table == RssItem.LibraryNotice.TABLE_NAME) {
            orderBy = RssItem.Common.COLUMN_NAME_PUBLISH_DATE + " DESC ";
        }
        Cursor results = readDatabase.query(table, null, selection, selectionargs, null, null, orderBy);
        results.moveToFirst();
        return results;
    }

    private String getSelectString(String table, String selection) {
        StringBuilder stringBuilder = new StringBuilder();
        String query = "select "+
                RssItem.Common.COLUMN_NAME_ID+","+
                RssItem.Common.COLUMN_NAME_GUID+","+
                RssItem.Common.COLUMN_NAME_TITLE+","+
                RssItem.Common.COLUMN_NAME_LINK+","+
                RssItem.Common.COLUMN_NAME_PUBLISH_DATE+","+
                RssItem.Common.COLUMN_NAME_DESCRIPTION+","+
                RssItem.Common.COLUMN_NAME_IS_READ+
                " from "+
                table;
        stringBuilder.append(query);
        if (selection != null) {
            stringBuilder.append(" where " + selection);
        }

        return stringBuilder.toString();
    }

    // Main,Library 두 곳에서 지정한 조건에 해당하는 리스트를 모두 반환한다.
    @Override
    public List<RssItem> getNotice(String selection, String[] selectionargs) {
        List<RssItem> rssItemList = new ArrayList<>();
        String query = getSelectString(RssItem.MainNotice.TABLE_NAME,selection) +
                " union all " +
                getSelectString(RssItem.LibraryNotice.TABLE_NAME,selection) +
                " order by " +
                RssItem.Common.COLUMN_NAME_PUBLISH_DATE + " DESC ";
        //select문을 2개로 만들다보니 selectionargs도 두배로 복사해야한다.
        String[] doubleArgs = null;
        if (selectionargs != null) {
            int argsLength = selectionargs.length;
            doubleArgs = new String[argsLength*2];
            for (int i = 0; i < doubleArgs.length; i++) {
                doubleArgs[i] = selectionargs[i%argsLength];
            }
        }

        Cursor results = readDatabase.rawQuery(query,doubleArgs);
        results.moveToFirst();

        while (!results.isAfterLast()){
            RssItem rssItem = new RssItem(results);
            rssItemList.add(rssItem);
            results.moveToNext();
        };
        results.close();
        return rssItemList;
    }

}
