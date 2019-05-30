package com.felix.imgurexplorer.request.response;

import com.felix.imgurexplorer.model.Image;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageSearchResponse {

    @SerializedName("data")
    @Expose()
    private List<Image> images;

    public List<Image> getImages() {
        return images;
    }

    @Override
    public String toString() {
        return "ImageSearchResponse{" +
                "images=" + images +
                '}';
    }
}
