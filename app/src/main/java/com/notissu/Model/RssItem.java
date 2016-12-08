package com.notissu.Model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.rometools.rome.feed.synd.SyndEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by forhack on 2016-11-09.
 */

public class RssItem implements Parcelable{
    //id는 db로부터 읽어왔을 때만 값을 갖는다.
    //서버로 읽어왔을 때는 값을갖지 않는다.
    private int id;
    private String guid;
    private String title;
    private String link;
    private String description;
    private long publishDate;
    //MainNotice에서만 값을 가지고
    //Library에서는 값을 가지지 않는다.
    private String category;

    public RssItem(String guid, String title, String link, String description, long publishDate) {
        this.guid = guid;
        this.title = title;
        this.link = link;
        this.description = description;
        this.publishDate = publishDate;
    }

    //커서가 인자인 생성자는 DB이외에는 생성되지 않는다.
    public RssItem(Cursor results) {
        int index = results.getColumnIndex(Common.COLUMN_NAME_ID);
        this.id = results.getInt(index);
        index = results.getColumnIndex(Common.COLUMN_NAME_GUID);
        this.guid = results.getString(index);
        index = results.getColumnIndex(Common.COLUMN_NAME_TITLE);
        this.title = results.getString(index);
        index = results.getColumnIndex(Common.COLUMN_NAME_LINK);
        this.link = results.getString(index);
        index = results.getColumnIndex(Common.COLUMN_NAME_PUBLISH_DATE);
        this.publishDate = results.getLong(index);
        index = results.getColumnIndex(Common.COLUMN_NAME_DESCRIPTION);
        this.description = results.getString(index);
    }


    protected RssItem(Parcel in) {
        id = in.readInt();
        guid = in.readString();
        title = in.readString();
        link = in.readString();
        description = in.readString();
        publishDate = in.readLong();
        category = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(guid);
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(description);
        dest.writeLong(publishDate);
        dest.writeString(category);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RssItem> CREATOR = new Creator<RssItem>() {
        @Override
        public RssItem createFromParcel(Parcel in) {
            return new RssItem(in);
        }

        @Override
        public RssItem[] newArray(int size) {
            return new RssItem[size];
        }
    };

    public long getPublishDate() {
        return publishDate;
    }

    public String getPublishDateShort() {
        Date date = new Date(publishDate);
        SimpleDateFormat format = new SimpleDateFormat("MM.dd");
        return format.format(date);
    }

    public String getPublishDateLong() {
        Date date = new Date(publishDate);
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm");
        return format.format(date);
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGuid() {
        return guid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    //인자로 넘어온 List<SyndEntry>를 List<RssItem>로 변환하는 메소드
    public static List<RssItem> toRssList(List<SyndEntry> syndEntries) {
        List<RssItem> rssItems = new ArrayList<>();
        for (int i = 0; i < syndEntries.size(); i++) {
            SyndEntry se = syndEntries.get(i);
            long publishDate;
            if (se.getPublishedDate() == null) {
                publishDate = 0;
            } else {
                publishDate = se.getPublishedDate().getTime();
            }

            RssItem rssItem = new RssItem(se.getUri(),se.getTitle(),se.getLink(),
                    se.getDescription().getValue(), publishDate);
            rssItems.add(rssItem);
        }
        return rssItems;
    }

    public static class Common {
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_GUID = "guid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_LINK = "link";
        public static final String COLUMN_NAME_PUBLISH_DATE = "published";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    }
    
    
    /*
    COLUMN_NAME_ID : 테이블의 고유 숫자
    COLUMN_NAME_GUID : Item 식별자
    COLUMN_NAME_TITLE : item 제목
    COLUMN_NAME_LINK : 이동 링크
    COLUMN_NAME_PUBLISH_DATE : 작성된 시간
    COLUMN_NAME_DESCRIPTION : 설명
    COLUMN_NAME_CATEGORY : 카테고리(전체,학사,장학 등)
    */
    public static class MainNotice {
        public static final String TABLE_NAME = "main_notice";
        public static final String COLUMN_NAME_ID = Common.COLUMN_NAME_ID;
        public static final String COLUMN_NAME_GUID = Common.COLUMN_NAME_GUID;
        public static final String COLUMN_NAME_TITLE = Common.COLUMN_NAME_TITLE;
        public static final String COLUMN_NAME_LINK = Common.COLUMN_NAME_LINK;
        public static final String COLUMN_NAME_PUBLISH_DATE = Common.COLUMN_NAME_PUBLISH_DATE;
        public static final String COLUMN_NAME_DESCRIPTION = Common.COLUMN_NAME_DESCRIPTION;
        public static final String COLUMN_NAME_CATEGORY = "category";
    }

    /*
    COLUMN_NAME_ID : 테이블의 고유 숫자
    COLUMN_NAME_GUID : Item 식별자
    COLUMN_NAME_TITLE : item 제목
    COLUMN_NAME_LINK : 이동 링크
    COLUMN_NAME_PUBLISH_DATE : 작성된 시간
    COLUMN_NAME_DESCRIPTION : 설명
    */
    public static class LibraryNotice {
        public static final String TABLE_NAME = "library_notice";
        public static final String COLUMN_NAME_ID = Common.COLUMN_NAME_ID;
        public static final String COLUMN_NAME_GUID = Common.COLUMN_NAME_GUID;
        public static final String COLUMN_NAME_TITLE = Common.COLUMN_NAME_TITLE;
        public static final String COLUMN_NAME_LINK = Common.COLUMN_NAME_LINK;
        public static final String COLUMN_NAME_PUBLISH_DATE = Common.COLUMN_NAME_PUBLISH_DATE;
        public static final String COLUMN_NAME_DESCRIPTION = Common.COLUMN_NAME_DESCRIPTION;
    }

    /*
    COLUMN_NAME_ID : 테이블의 고유 숫자
    COLUMN_NAME_GUID : Item 식별자
    */
    public static class Starred {
        public static final String TABLE_NAME = "starred";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
    }

    /*
    COLUMN_NAME_ID : 테이블의 고유 숫자
    COLUMN_NAME_GUID : Item 식별자
    */
    public static class Keyword {
        public static final String TABLE_NAME = "keyword";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_KEYWORD = "keyword";
    }
}

