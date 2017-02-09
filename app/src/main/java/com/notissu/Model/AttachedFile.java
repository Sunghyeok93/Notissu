package com.notissu.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AttachedFile extends RealmObject {
    private static final String TAG = AttachedFile.class.getSimpleName();
    private String title;
    private String url;

    public AttachedFile() {
    }

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
