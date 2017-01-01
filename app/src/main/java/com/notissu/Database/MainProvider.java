package com.notissu.Database;

import com.notissu.Model.RssItem;

import java.util.List;

/**
 * Created by forhack on 2016-12-16.
 */

public interface MainProvider extends NoticeProvider{
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
            NOTICE_SSU_ALL, NOTICE_SSU_HACKSA, NOTICE_SSU_JANGHACK, NOTICE_SSU_KUCKJE,
            NOTICE_SSU_WAEKUCK, NOTICE_SSU_MOJIP, NOTICE_SSU_KYONE,
            NOTICE_SSU_KYOWAE, NOTICE_SSU_BONGSA
    };

    List<RssItem> getSsuNotice(String category);
    //Main 공지사항 가져오기
    List<RssItem> getNotice(String category);
}
