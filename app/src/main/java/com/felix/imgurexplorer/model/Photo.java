package com.felix.imgurexplorer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Photo implements Parcelable {

    private String title;
    private String id;

    public Photo(String title, String id) {
        this.title = title;
        this.id = id;
    }

    protected Photo(Parcel in) {
        title = in.readString();
        id = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(id);
    }
}
