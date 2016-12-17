package com.notissu.Database;

import com.notissu.Model.RssItem;

/**
 * Created by forhack on 2016-12-16.
 */

public interface NoticeProvider {
    //인자로 들어온 RssItem을 업데이트한다.
    //main인지 library인지 알아서 찾는다.
    int updateNotice(RssItem rssItem);

    //안 읽은 공지사항의 개수 반환
    int getNotReadCount(String table);

    //안읽은 공지사항 모두 업데이트 하도록 한다.
    int updateAllReadCount(String table);
}
