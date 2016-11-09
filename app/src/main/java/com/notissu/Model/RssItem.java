package com.notissu.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by forhack on 2016-11-09.
 */

public class RssItem implements Parcelable{
    String title;
    String link;
    String description;
    String date;
    String author;

    public RssItem(String title, String link, String description, String date, String author) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.date = date;
        this.author = author;
    }

    protected RssItem(Parcel in) {
        title = in.readString();
        link = in.readString();
        description = in.readString();
        date = in.readString();
        author = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeString(author);
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

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }
}
