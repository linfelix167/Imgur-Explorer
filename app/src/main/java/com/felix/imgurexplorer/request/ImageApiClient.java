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
    private MutableLiveData<List<Image>> mPhotos;
    private RetrievePhotosRunnable mRetrievePhotosRunnable;

    public static ImageApiClient getInstance() {
        if (instance == null) {
            instance = new ImageApiClient();
        }
        return instance;
    }

    private ImageApiClient() {
        mPhotos = new MutableLiveData<>();
    }

    public LiveData<List<Image>> getPhotos() {
        return mPhotos;
    }

    public void searchPhotoApi(int pageNumber, String query) {
        if (mRetrievePhotosRunnable != null) {
            mRetrievePhotosRunnable = null;
        }
        mRetrievePhotosRunnable = new RetrievePhotosRunnable(pageNumber, query);
        final Future handler = AppExecutors.get().networkIO().submit(mRetrievePhotosRunnable);

        // Set a timeout for the data refresh
        AppExecutors.get().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrievePhotosRunnable implements Runnable {

        private int pageNumber;
        private String query;
        private boolean cancelRequest;

        public RetrievePhotosRunnable(int pageNumber, String query) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getPhotos(pageNumber, query).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<Image> list = new ArrayList<>(((ImageSearchResponse)response.body()).getImages());
                    if (pageNumber == 1) {
                        mPhotos.postValue(list);
                    } else {
                        List<Image> currentImages = mPhotos.getValue();
                        currentImages.addAll(list);
                        mPhotos.postValue(currentImages);
                    }

                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: error " + error);
                    mPhotos.postValue(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                mPhotos.postValue(null);
            }
        }

        private Call<ImageSearchResponse> getPhotos(int pageNumber, String query) {
            return ServiceGenerator.getImageApi().searchPhoto(
                    pageNumber,
                    query);
        }

        private void cancelRequest() {
            cancelRequest = true;
        }
    }
}
