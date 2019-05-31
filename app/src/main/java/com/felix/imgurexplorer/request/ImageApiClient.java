package com.felix.imgurexplorer.request;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.felix.imgurexplorer.util.AppExecutors;
import com.felix.imgurexplorer.model.Image;
import com.felix.imgurexplorer.request.response.ImageSearchResponse;
import com.felix.imgurexplorer.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class ImageApiClient {

    private static final String TAG = ImageApiClient.class.getSimpleName();

    private static ImageApiClient instance;
    private MutableLiveData<List<Image>> mImages;
    private RetrieveImagesRunnable mRetrieveImagesRunnable;

    public static ImageApiClient getInstance() {
        if (instance == null) {
            instance = new ImageApiClient();
        }
        return instance;
    }

    private ImageApiClient() {
        mImages = new MutableLiveData<>();
    }

    public LiveData<List<Image>> getImages() {
        return mImages;
    }

    public void searchPhotoApi(int pageNumber, String query) {
        if (mRetrieveImagesRunnable != null) {
            mRetrieveImagesRunnable = null;
        }
        mRetrieveImagesRunnable = new RetrieveImagesRunnable(pageNumber, query);
        final Future handler = AppExecutors.get().networkIO().submit(mRetrieveImagesRunnable);

        // Set a timeout for the data refresh
        AppExecutors.get().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public void cancelRequest() {
        if (mRetrieveImagesRunnable != null) {
            mRetrieveImagesRunnable.cancelRequest();
        }
    }

    private class RetrieveImagesRunnable implements Runnable {

        private int pageNumber;
        private String query;
        private boolean cancelRequest;

        public RetrieveImagesRunnable(int pageNumber, String query) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getImages(pageNumber, query).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<Image> list = new ArrayList<>(((ImageSearchResponse)response.body()).getImages());
                    if (pageNumber == 1) {
                        mImages.postValue(list);
                    } else {
                        List<Image> currentImages = mImages.getValue();
                        currentImages.addAll(list);
                        mImages.postValue(currentImages);
                    }

                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: error " + error);
                    mImages.postValue(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                mImages.postValue(null);
            }
        }

        private Call<ImageSearchResponse> getImages(int pageNumber, String query) {
            return ServiceGenerator.getImageApi().searchPhoto(
                    pageNumber,
                    query);
        }

        private void cancelRequest() {
            cancelRequest = true;
        }
    }
}
