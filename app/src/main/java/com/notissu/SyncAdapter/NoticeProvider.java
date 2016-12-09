package com.notissu.SyncAdapter;

import com.notissu.Model.RssItem;

import java.util.List;

/**
 * Created by forhack on 2016-12-04.
 */

public interface NoticeProvider {
    String NOTICE_SSU_ALL = "전체";
    String NOTICE_SSU_HACKSA = "학사";
    String NOTICE_SSU_JANGHACK = "장학";
    String NOTICE_SSU_KUCKJE = "국제교류";
    String NOTICE_SSU_WAEKUCK = "외국인유학생";
    String NOTICE_SSU_MOJIP = "모집·채용";
    String NOTICE_SSU_KYONE = "교내행사";
    String NOTICE_SSU_KYOWAE = "교외행사";
    String NOTICE_SSU_BONGSA = "봉사";
    String[] NOTICE_CATEGORY = {
            NOTICE_SSU_HACKSA, NOTICE_SSU_JANGHACK, NOTICE_SSU_KUCKJE,
            NOTICE_SSU_WAEKUCK, NOTICE_SSU_MOJIP, NOTICE_SSU_KYONE,
            NOTICE_SSU_KYOWAE, NOTICE_SSU_BONGSA
    };

    //메인 공지사항을 불러오는데, 카테고리를 인자로 넣어서 불러올 수 있다.
    List<RssItem> getMainNotice(String category);

    //도서관의 공지사항을 불러온다.
    List<RssItem> getLibraryNotice();

    //즐겨찾기한 공지사항을 불러온다.
    List<RssItem> getStarredNotice();

    //키워드로 등록한 공지사항을 불러온다.
    List<RssItem> getKeywordNotice(String keyword);



}
