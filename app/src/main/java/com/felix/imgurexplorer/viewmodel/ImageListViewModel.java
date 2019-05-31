package com.felix.imgurexplorer.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.felix.imgurexplorer.model.Image;
import com.felix.imgurexplorer.repository.ImageRepository;

import java.util.List;

public class ImageListViewModel extends ViewModel {

    private ImageRepository mImageRepository;
    private boolean mIsPerformingQuery;

    public ImageListViewModel() {
        mImageRepository = ImageRepository.getInstance();
        mIsPerformingQuery = false;
    }

    public LiveData<List<Image>> getImages() {
        return mImageRepository.getImages();
    }

    public void searchImagesApi(int pageNumber, String query) {
        mIsPerformingQuery = true;
        mImageRepository.searchImagesApi(pageNumber, query);
    }

    public boolean isIsPerformingQuery() {
        return mIsPerformingQuery;
    }

    public void setIsPerformingQuery(boolean mIsPerformingQuery) {
        this.mIsPerformingQuery = mIsPerformingQuery;
    }

    public void searchNextPage() {
        if (!isIsPerformingQuery() && !isQueryExhausted().getValue()) {
            mImageRepository.searchNextPage();
        }
    }

    public LiveData<Boolean> isQueryExhausted() {
        return mImageRepository.isQueryExhausted();
    }
}
