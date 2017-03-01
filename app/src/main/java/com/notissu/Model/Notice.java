package com.notissu.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Notice extends RealmObject implements Parcelable {
    @Ignore
    private static final String TAG = Notice.class.getSimpleName();
    @PrimaryKey
    private int id;
    private String title;
    private String date;
    private boolean isStarred;
    private boolean isRead;

    public Notice(int id, String title, String date, boolean isStarred, boolean isRead) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.isStarred = isStarred;
        this.isRead = isRead;
    }

    public Notice() {
    }

    protected Notice(Parcel in) {
        id = in.readInt();
        title = in.readString();
        date = in.readString();
        isStarred = in.readByte() != 0;
        isRead = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeByte((byte) (isStarred ? 1 : 0));
        dest.writeByte((byte) (isRead ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Notice> CREATOR = new Creator<Notice>() {
        @Override
        public Notice createFromParcel(Parcel in) {
            return new Notice(in);
        }

        @Override
        public Notice[] newArray(int size) {
            return new Notice[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getShortDate() {
        String pieceDate[] = date.split("-");
        return pieceDate[1] + "-" + pieceDate[2];
    }

    public String getDate() {
        return date;
    }

    public boolean isStarred() {
        return isStarred;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void setStarred(boolean starred) {
        isStarred = starred;
    }

    private static void setProperty(List<Notice> noticeList) {
        RealmResults<Notice> resultList = Realm.getDefaultInstance().where(Notice.class).findAll();
        for (Notice notice : noticeList) {
            for (Notice resultNotice : resultList) {
                if (notice.getId() == resultNotice.getId()) {
                    notice.setStarred(resultNotice.isStarred());
                    notice.setRead(resultNotice.isRead());
                }
            }
        }
    }

    public static List<Notice> fromJson(String response) {
        Type listType = new TypeToken<List<Notice>>() {
        }.getType();
        List<Notice> noticeList = new Gson().fromJson(response, listType);
        setProperty(noticeList);
        return noticeList;
    }

    public static List<Notice> fromHtml(String response) {
        List<Notice> noticeList = new ArrayList<>();
        Document doc = Jsoup.parse(response);
        Elements bbsList = doc.select("ol.bbs-list li.first-child");
        String firstDelimiter = "messageId=";
        String lastDelimiter = "&amp;curPage";

        for (Element element : bbsList) {
            String tagA = element.select("a").toString();
            int firstIndex = tagA.indexOf(firstDelimiter) + firstDelimiter.length();
            int lastIndex = tagA.indexOf(lastDelimiter);
            int id = Integer.valueOf(tagA.substring(firstIndex, lastIndex));
            String title = element.select("a").text();
            String date = element.select("span").text();
            Notice notice = new Notice(id, title, date, false, false);
            noticeList.add(notice);
        }
        setProperty(noticeList);
        return noticeList;
    }
}
