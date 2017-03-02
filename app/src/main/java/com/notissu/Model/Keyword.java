package com.notissu.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Keyword{
    private String keyword;
    private String hash;

    public Keyword() {
    }

    public Keyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTitle() {
        return keyword;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        Keyword otherKeyword = (Keyword) obj;
        return keyword.equals(otherKeyword.getTitle());
    }

    public static List<Keyword> fromJson(String response) {
        List<Keyword> keywordList = new ArrayList<>();
        try {
            Type listType = new TypeToken<List<Keyword>>() {
            }.getType();
            keywordList = new Gson().fromJson(response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keywordList;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
