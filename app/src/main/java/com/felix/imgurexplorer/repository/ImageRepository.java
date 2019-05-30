package com.felix.imgurexplorer.repository;

import android.arch.lifecycle.LiveData;

import com.felix.imgurexplorer.model.Image;
import com.felix.imgurexplorer.request.ImageApiClient;

import java.util.List;

public class ImageRepository {

    private static ImageRepository instance;
    private ImageApiClient mImageApiClient;
    private String mQuery;
    private int mPageNumber;

    public static ImageRepository getInstance() {
        if (instance == null) {
            instance = new ImageRepository();
        }
        return instance;
    }

    private ImageRepository() {
        mImageApiClient = ImageApiClient.getInstance();
    }

    public LiveData<List<Image>> getImages() {
        return  mImageApiClient.getPhotos();
    }

    public void searchImagesApi(int pageNumber, String query) {
        if (pageNumber == 0) {
            pageNumber = 1;
        }
        mQuery = query;
        mPageNumber = pageNumber;
        mImageApiClient.searchPhotoApi(pageNumber, query);
    }

    public void searchNextPage() {
        searchImagesApi(mPageNumber + 1, mQuery);
    }

    public void cancelRequest() {
        mImageApiClient.cancelRequest();
    }
}
