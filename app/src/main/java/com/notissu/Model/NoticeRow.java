package com.notissu.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dnfld on 2016-11-07.
 */

public class NoticeRow implements Parcelable{
    String title;
    String time;

    public NoticeRow(String title, String time) {
        this.title = title;
        this.time = time;
    }

    protected NoticeRow(Parcel in) {
        title = in.readString();
        time = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoticeRow> CREATOR = new Creator<NoticeRow>() {
        @Override
        public NoticeRow createFromParcel(Parcel in) {
            return new NoticeRow(in);
        }

        @Override
        public NoticeRow[] newArray(int size) {
            return new NoticeRow[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
