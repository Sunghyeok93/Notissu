package com.notissu.Util;

import com.notissu.Model.NoticeRow;

import java.util.ArrayList;

/**
 * Created by forhack on 2016-10-21.
 */

public class Str {
    //숭실대 공지사항 String array의 name
    public static final String RES_SSU_NOTICES = "ssu_notices";
    public static final ArrayList<NoticeRow> ALL_SSU_NOTICES = new ArrayList<>();
    public static final ArrayList<NoticeRow> HACKSA_SSU_NOTICES = new ArrayList<>();
    public static final ArrayList<NoticeRow> JANGHACK_SSU_NOTICES = new ArrayList<>();
    public static final ArrayList<NoticeRow> KUCKJE_SSU_NOTICES = new ArrayList<>();
    public static final ArrayList<NoticeRow> MOJIP_SSU_NOTICES = new ArrayList<>();
    public static final ArrayList<NoticeRow> KYONE_SSU_NOTICES = new ArrayList<>();
    public static final ArrayList<NoticeRow> KYOWAE_SSU_NOTICES = new ArrayList<>();
    public static final ArrayList<NoticeRow> BONGSA_SSU_NOTICES = new ArrayList<>();
    static {
        ALL_SSU_NOTICES.add(new NoticeRow(
                "[모집·채용]졸업 선배 멘토링 안내[외국계항공사(해외취업)]",
                "11.09"));
        ALL_SSU_NOTICES.add(new NoticeRow(
                "[모집·채용]졸업 선배 멘토링 안내[현대상선/농협정보시스템]",
                "11.09"));
        ALL_SSU_NOTICES.add(new NoticeRow(
                "[모집·채용]실전창업프로젝트 [내가 CEO라면 이렇게 마케팅한다!] 참가자 모집",
                "11.08"));
        ALL_SSU_NOTICES.add(new NoticeRow(
                "[모집·채용]다우케미칼 CDP(Commercial Development...",
                "11.08"));
        ALL_SSU_NOTICES.add(new NoticeRow(
                "[교내행사]숭실대 재학생 대상 진로 및 학습 프로그램- 나너길",
                "11.08"));
        ALL_SSU_NOTICES.add(new NoticeRow(
                "[교내행사]2016학년도 숭실기독인연합 '아빠를 위한 문화제' 참여 안내",
                "11.08"));
        ALL_SSU_NOTICES.add(new NoticeRow(
                "[교내행사]11월 9일 숭실가족수요예배 안내",
                "11.08"));
        ALL_SSU_NOTICES.add(new NoticeRow(
                "[모집·채용]2016년 교내 인턴조교 채용 공고",
                "2016.11.08"));
        ALL_SSU_NOTICES.add(new NoticeRow(
                "[모집·채용]지학사 인사총무노무 신입 추천 (11. 14 월 오전 09시 마감)",
                "2016.11.08"));
        HACKSA_SSU_NOTICES.add(new NoticeRow("학사","11-07"));
        JANGHACK_SSU_NOTICES.add(new NoticeRow("장학","11-08"));
        KUCKJE_SSU_NOTICES.add(new NoticeRow("국제","11-09"));
        MOJIP_SSU_NOTICES.add(new NoticeRow("모집","11-00"));
        KYONE_SSU_NOTICES.add(new NoticeRow("교내","11-01"));
        KYOWAE_SSU_NOTICES.add(new NoticeRow("교외","11-02"));
        BONGSA_SSU_NOTICES.add(new NoticeRow("봉사","11-03"));


    }
}
