package com.notissu.Database;

import com.notissu.Model.RssItem;

import java.util.List;

/**
 * Created by forhack on 2016-12-04.
 */

public interface NoticeProvider {


    //메인 공지사항을 불러오는데, 카테고리를 인자로 넣어서 불러올 수 있다.
    List<RssItem> getMainNotice(String category);

    //도서관의 공지사항을 불러온다.
    List<RssItem> getLibraryNotice();

    //즐겨찾기한 공지사항을 불러온다.
    List<RssItem> getStarredNotice();

    //키워드로 등록한 공지사항을 불러온다.
    List<RssItem> getKeywordNotice(String keyword);

    List<RssItem> getSsuNotice(String category);

}
