package com.notissu.Database;

import com.notissu.Model.RssItem;

import java.util.List;

/**
 * Created by forhack on 2016-12-16.
 */

public interface LibraryProvider extends NoticeProvider{
    //Library 공지사항 가져오기
    List<RssItem> getNotice();

}
