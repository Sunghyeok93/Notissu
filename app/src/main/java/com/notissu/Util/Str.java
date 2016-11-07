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
        ALL_SSU_NOTICES.add(new NoticeRow("전체","11-03"));
        ALL_SSU_NOTICES.add(new NoticeRow("전체","11-04"));
        ALL_SSU_NOTICES.add(new NoticeRow("전체","11-05"));
        ALL_SSU_NOTICES.add(new NoticeRow("전체","11-06"));
        HACKSA_SSU_NOTICES.add(new NoticeRow("학사","11-07"));
        JANGHACK_SSU_NOTICES.add(new NoticeRow("장학","11-08"));
        KUCKJE_SSU_NOTICES.add(new NoticeRow("국제","11-09"));
        MOJIP_SSU_NOTICES.add(new NoticeRow("모집","11-00"));
        KYONE_SSU_NOTICES.add(new NoticeRow("교내","11-01"));
        KYOWAE_SSU_NOTICES.add(new NoticeRow("교외","11-02"));
        BONGSA_SSU_NOTICES.add(new NoticeRow("봉사","11-03"));


    }
}
