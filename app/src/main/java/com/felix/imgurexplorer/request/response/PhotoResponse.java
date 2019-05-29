package com.felix.imgurexplorer.request.response;

import com.felix.imgurexplorer.model.Photo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoResponse {

    @SerializedName("data")
    @Expose()
    private Photo photo;

    public Photo getPhoto() {
        return photo;
    }

    @Override
    public String toString() {
        return "PhotoResponse{" +
                "photo=" + photo +
                '}';
    }
}
