package com.notissu.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Keyword extends RealmObject {
    @PrimaryKey
    private String keyword;

    public Keyword() {
    }

    public Keyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTitle() {
        return keyword;
    }

    public static List<Keyword> fromJson(String response) {
        Type listType = new TypeToken<List<Keyword>>() {
        }.getType();
        return new Gson().fromJson(response, listType);
    }
}
