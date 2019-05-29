package com.felix.imgurexplorer.request.response;

import com.felix.imgurexplorer.model.Photo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoSearchResponse {

    @SerializedName("data")
    @Expose()
    private List<Photo> photos;

    public List<Photo> getPhotos() {
        return photos;
    }

    @Override
    public String toString() {
        return "PhotoSearchResponse{" +
                "photos=" + photos +
                '}';
    }
}
