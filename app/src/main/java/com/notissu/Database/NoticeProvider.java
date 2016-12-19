package com.notissu.Database;

import com.notissu.Model.RssItem;

import java.util.List;

/**
 * Created by forhack on 2016-12-16.
 */

public interface NoticeProvider {

    //입력받은 RSS를 DB에 삽입하는 메소드
    //실패했을 때 -1 반환
    long addNotice(RssItem isExist);

    //입력받은 RSS를 DB에 업데이트(수정)하는 메소드
    //일치하는 row가 없으면 0 반환
    int updateNotice(RssItem isExist);

    //인자로 넣은 GUID와 일치하는 RSS 삭제
    //일치하는 row가 없으면 0 반환
    int deleteNotice(String guid);

    //안 읽은 공지사항의 개수 반환
    int getNotReadCount();

    //안읽은 공지사항 모두 읽음으로 업데이트 하도록 한다.
    int updateAllReadCount();
}
