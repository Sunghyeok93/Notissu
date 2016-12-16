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
            NOTICE_SSU_HACKSA, NOTICE_SSU_JANGHACK, NOTICE_SSU_KUCKJE,
            NOTICE_SSU_WAEKUCK, NOTICE_SSU_MOJIP, NOTICE_SSU_KYONE,
            NOTICE_SSU_KYOWAE, NOTICE_SSU_BONGSA
    };

    //메인 공지사항을 불러오는데, 카테고리를 인자로 넣어서 불러올 수 있다.
    List<RssItem> getMainNotice(String category);

    //입력받은 RSS를 DB에 삽입하는 메소드
    //실패했을 때 -1 반환
    long addMainNotice(RssItem isExist);

    //입력받은 RSS를 DB에 업데이트(수정)하는 메소드
    //일치하는 row가 없으면 0 반환
    int updateMainNotice(RssItem isExist);

    //인자로 넣은 GUID와 일치하는 RSS 삭제
    //일치하는 row가 없으면 0 반환
    int deleteMainNotice(String guid);

    List<RssItem> getSsuNotice(String category);

    //안 읽은 공지사항의 개수 반환
    int getMainNotReadCount();


}
