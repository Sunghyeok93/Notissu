package com.notissu.UI.NoticeTab;

/**
 * Created by forhack on 2017-01-01.
 */

public interface NoticeTabContract {
    String NOTICE_SSU_ALL = "전체";
    String NOTICE_SSU_STUDENT = "학사";
    String NOTICE_SSU_SCHOLARSHIP = "장학";
    String NOTICE_SSU_GLOBAL = "국제교류";
    String NOTICE_SSU_FOREIGN = "외국인유학생";
    String NOTICE_SSU_RECRUIT = "모집·채용";
    String NOTICE_SSU_INNER = "교내행사";
    String NOTICE_SSU_OUTER = "교외행사";
    String NOTICE_SSU_VOLUNTEER = "봉사";
    String[] NOTICE_CATEGORY = {
            NOTICE_SSU_ALL, NOTICE_SSU_STUDENT, NOTICE_SSU_SCHOLARSHIP, NOTICE_SSU_GLOBAL,
            NOTICE_SSU_FOREIGN, NOTICE_SSU_RECRUIT, NOTICE_SSU_INNER,
            NOTICE_SSU_OUTER, NOTICE_SSU_VOLUNTEER
    };
    String NOTICE_SSU_LIBRARY = "도서관";
    String KEY_TITLE = "KEY_TITLE";
    String KEY_FLAG = "KEY_FLAG";

    interface View {

        void addTabs(String[] noticeCategory);

        int getTabCount();

        void setAdapter(NoticeTabPagerAdapter noticeTabPagerAdapter);
    }

    interface Presenter {

        void setAdapter(NoticeTabPagerAdapter noticeTabPagerAdapter);

        void setTab(String[] noticeCategory);
    }

}
