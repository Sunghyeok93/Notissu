package com.notissu.Database;

import com.notissu.Model.RssItem;

import java.util.List;

/**
 * Created by forhack on 2016-12-04.
 */

/*필요할 때 DB연결해서 바로 전달해줘도 되는데 이렇게 한 이유는
* 그냥 순수하게 분리, 그 이유 밖에 없는것 같다.*/

public class NoticeProviderImpl {

    public NoticeProviderImpl() {}

/*    public List<RssItem> getSsuNotice(String category) {
        switch (category) {
            case "전체":
                return getMainNotice(NOTICE_SSU_ALL);
            case "학사":
                return getMainNotice(NOTICE_SSU_HACKSA);
            case "장학":
                return getMainNotice(NOTICE_SSU_JANGHACK);
            case "국제교류":
                return getMainNotice(NOTICE_SSU_KUCKJE);
            case "모집,채용":
                return getMainNotice(NOTICE_SSU_MOJIP);
            case "교내행사":
                return getMainNotice(NOTICE_SSU_KYONE);
            case "교외행사":
                return getMainNotice(NOTICE_SSU_KYOWAE);
            case "봉사":
                return getMainNotice(NOTICE_SSU_BONGSA);
            default:
                return getMainNotice(NOTICE_SSU_ALL);
        }
    }*/
}
