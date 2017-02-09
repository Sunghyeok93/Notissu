package com.notissu.Model;

import com.google.gson.Gson;

import java.util.List;

public class NoticeDetail {
    private static final String TAG = NoticeDetail.class.getSimpleName();
    private String title;
    private String date;
    private String contents;
    private List<AttachedFile> attached_files;

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getContents() {
        return contents;
    }

    public List<AttachedFile> getAttachedFileList() {
        return attached_files;
    }

    public static NoticeDetail fromJson(String response) {
        return new Gson().fromJson(response,NoticeDetail.class);
    }
}
