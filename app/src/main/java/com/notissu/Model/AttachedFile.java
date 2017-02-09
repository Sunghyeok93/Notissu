package com.notissu.Model;

public class AttachedFile {
    private static final String TAG = AttachedFile.class.getSimpleName();
    private String title;
    private String url;

    public AttachedFile(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
