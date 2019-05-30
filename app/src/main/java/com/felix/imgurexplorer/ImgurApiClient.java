package com.felix.imgurexplorer;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.felix.imgurexplorer.model.Photo;

import java.util.List;

import retrofit2.Response;

public class ImgurApiClient {

    private static final String TAG = ImgurApiClient.class.getSimpleName();

    private static ImgurApiClient instance;
    private MutableLiveData<List<Photo>> mPhotos;
    private RetrievePhotosRunnable mRetrievePhotosRunnable;

    public static ImgurApiClient getInstance() {
        if (instance == null) {
            instance = new ImgurApiClient();
        }
        return instance;
    }

    private ImgurApiClient() {
        mPhotos = new MutableLiveData<>();
    }

    public LiveData<List<Photo>> getPhotos() {
        return mPhotos;
    }

    public void searchPhotoApi(int pageNumber, String query) {
        if (mRetrievePhotosRunnable != null) {
            mRetrievePhotosRunnable = null;
        }
        mRetrievePhotosRunnable = new RetrievePhotosRunnable(pageNumber, query);

    }

    private class RetrievePhotosRunnable implements Runnable {

        private String query;
        private int pageNumber;
        private boolean cancelRequest;

        public RetrievePhotosRunnable(int pageNumber, String query) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {

            } catch (Exception e) {

            }
        }
    }
}
