package com.notissu.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Keyword extends RealmObject {
    @PrimaryKey
    private String title;

    public Keyword() {
    }

    public Keyword(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
