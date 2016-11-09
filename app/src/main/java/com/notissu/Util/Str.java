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
        ALL_SSU_NOTICES.add(new NoticeRow(
                "[학사]2016-2학기 졸업예정자 연계전공 이수구분 변경 신청 안내",
                "2016.11.07"));
        ALL_SSU_NOTICES.add(new NoticeRow(
                "[학사]학사과정 수료생의 졸업예정자 등록 최종 신청 안내",
                "2016.11.07"));
        HACKSA_SSU_NOTICES.add(new NoticeRow(
                "[학사]2016-2학기 졸업예정자 연계전공 이수구분 변경 신청 안내",
                "2016.11.07"));
        HACKSA_SSU_NOTICES.add(new NoticeRow(
                "[학사]학사과정 수료생의 졸업예정자 등록 최종 신청 안내",
                "2016.11.07"));
        HACKSA_SSU_NOTICES.add(new NoticeRow(
                "[학사]국내대학 학점교류 실시 및 수강신청 안내(2016-겨울)",
                "10.31"));
        HACKSA_SSU_NOTICES.add(new NoticeRow(
                "[학사]2016년도 2차 종합보충훈련안내",
                "10.27"));
        HACKSA_SSU_NOTICES.add(new NoticeRow(
                "[학사]졸업예정자 중 조기취업자의 출석 인정 시행지침 안내",
                "10.26"));
        HACKSA_SSU_NOTICES.add(new NoticeRow(
                "[학사]2016-겨울계절제 사전 수요조사 안내",
                "10.26"));
        HACKSA_SSU_NOTICES.add(new NoticeRow(
                "[학사]2016「대학생 핵심역량 진단(K-CESA)」참여자 추가 모집",
                "10.25"));
        HACKSA_SSU_NOTICES.add(new NoticeRow(
                "[학사]2016학년도 공감과 소통 글쓰기 공모전(부모님 평전, 멘토와의 대화)...",
                "10.24"));
        HACKSA_SSU_NOTICES.add(new NoticeRow(
                "[학사]제 3 회 숭실독서향연 안내",
                "10.24"));
        HACKSA_SSU_NOTICES.add(new NoticeRow(
                "[학사]2016 겨울계절제 운영일정 및 유의사항 안내",
                "10.19"));
        HACKSA_SSU_NOTICES.add(new NoticeRow(
                "[학사]2016학년도 2학기 교직과정 이수 신청 안내",
                "10.13"));


        JANGHACK_SSU_NOTICES.add(new NoticeRow("장학","11-08"));
        KUCKJE_SSU_NOTICES.add(new NoticeRow("국제","11-09"));
        MOJIP_SSU_NOTICES.add(new NoticeRow("모집","11-00"));
        KYONE_SSU_NOTICES.add(new NoticeRow("교내","11-01"));
        KYOWAE_SSU_NOTICES.add(new NoticeRow("교외","11-02"));
        BONGSA_SSU_NOTICES.add(new NoticeRow("봉사","11-03"));


    }
}
