package com.notissu.Database;

import com.notissu.Model.RssItem;

/**
 * Created by forhack on 2016-12-16.
 */

public interface NoticeProvider {
    //인자로 들어온 RssItem을 업데이트한다.
    //main인지 library인지 알아서 찾는다.
    int updateNotice(RssItem rssItem);
}
