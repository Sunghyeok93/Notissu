package com.notissu.Model;

import com.google.gson.Gson;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class NoticeDetail extends RealmObject{
    private static final String TAG = NoticeDetail.class.getSimpleName();
    @PrimaryKey
    private int notice_id;
    private String title;
    private String date;
    private String contents;
    private RealmList<AttachedFile> attached_files;

    public int getNoticeId() {
        return notice_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getContents() {
        return contents;
    }

    public RealmList<AttachedFile> getAttachedFileList() {
        return attached_files;
    }

    public static NoticeDetail fromJson(String response) {
        return new Gson().fromJson(response,NoticeDetail.class);
    }
}
