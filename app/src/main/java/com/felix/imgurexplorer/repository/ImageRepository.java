package com.felix.imgurexplorer.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.felix.imgurexplorer.model.Image;
import com.felix.imgurexplorer.request.ImageApiClient;

import java.util.List;

public class ImageRepository {

    private static ImageRepository instance;
    private ImageApiClient mImageApiClient;
    private String mQuery;
    private int mPageNumber;
    private MutableLiveData<Boolean> mIsQueryExhausted = new MutableLiveData<>();
    private MediatorLiveData<List<Image>> mImages = new MediatorLiveData<>();

    public static ImageRepository getInstance() {
        if (instance == null) {
            instance = new ImageRepository();
        }
        return instance;
    }

    private ImageRepository() {
        mImageApiClient = ImageApiClient.getInstance();
        initMediators();
    }

    private void initMediators() {
        // add source for the API query in MainActivity
        LiveData<List<Image>> imageListApiSource = mImageApiClient.getImages();
        mImages.addSource(imageListApiSource, new Observer<List<Image>>() {
            @Override
            public void onChanged(@Nullable List<Image> images) {
                if (images != null) {
                    mImages.setValue(images);
                    doneQuery(images);
                } else {
                    doneQuery(null);
                }
            }
        });
    }

    public void doneQuery(List<Image> images) {
        if (images != null) {
            if (images.size() % 30 != 0) {
                mIsQueryExhausted.setValue(true);
            }
        } else {
            mIsQueryExhausted.setValue(true);
        }
    }

    public LiveData<Boolean> isQueryExhausted() {
        return mIsQueryExhausted;
    }

    public LiveData<List<Image>> getImages() {
        return  mImages;
    }

    public void searchImagesApi(int pageNumber, String query) {
        if (pageNumber == 0) {
            pageNumber = 1;
        }
        mQuery = query;
        mPageNumber = pageNumber;
        mIsQueryExhausted.setValue(false);
        mImageApiClient.searchPhotoApi(pageNumber, query);
    }

    public void searchNextPage() {
        searchImagesApi(mPageNumber + 1, mQuery);
    }
}
