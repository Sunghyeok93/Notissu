package com.notissu.Database;

import com.notissu.Model.RssItem;

import java.util.List;

/**
 * Created by forhack on 2016-12-16.
 */

public interface KeywordProvider {
    //지정 Keyword의 item들 가져오기
    List<RssItem> getKeyword(String keyword);

    //Keyword 목록 가져오기
    List<String> getKeyword();

    //입력받은 RSS를 DB에 삽입하는 메소드
    //실패했을 때 -1 반환
    long addKeyword(String keyword);

    //인자로 넣은 GUID와 일치하는 RSS 삭제
    //일치하는 row가 없으면 0 반환
    int deleteKeyword(String keyword);

    //안 읽은 공지사항의 개수 반환
    int getNotReadCount(String keyword);
}
