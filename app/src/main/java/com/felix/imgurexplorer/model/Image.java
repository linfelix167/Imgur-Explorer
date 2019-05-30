package com.felix.imgurexplorer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {

    private String title;
    private String id;
    private boolean is_album;
    private String cover;

    public Image(String title, String id, boolean is_album, String cover) {
        this.title = title;
        this.id = id;
        this.is_album = is_album;
        this.cover = cover;
    }

    protected Image(Parcel in) {
        title = in.readString();
        id = in.readString();
        is_album = in.readByte() != 0;
        cover = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIs_album() {
        return is_album;
    }

    public void setIs_album(boolean is_album) {
        this.is_album = is_album;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(id);
        dest.writeByte((byte) (is_album ? 1 : 0));
        dest.writeString(cover);
    }
}
