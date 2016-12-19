package com.notissu.Database;

import android.database.Cursor;

import com.notissu.Model.RssItem;

import java.util.List;

/**
 * Created by forhack on 2016-12-16.
 */

public interface LowDBProvider {
    //DB에 있는 모든 RSS를 List형태로 반환하는 메소드
    Cursor getCursor(String table, String selection, String[] selectionargs);

    // Main,Library 두 곳에서 지정한 조건에 해당하는 리스트를 모두 반환한다.
    List<RssItem> getNotice(String selection, String[] selectionargs);

}
