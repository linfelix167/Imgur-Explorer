package com.felix.imgurexplorer.request.response;

import com.felix.imgurexplorer.model.Image;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageResponse {

    @SerializedName("data")
    @Expose()
    private Image image;

    public Image getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "ImageResponse{" +
                "image=" + image +
                '}';
    }
}
