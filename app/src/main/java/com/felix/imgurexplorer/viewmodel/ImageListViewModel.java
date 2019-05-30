package com.felix.imgurexplorer.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.felix.imgurexplorer.model.Image;
import com.felix.imgurexplorer.repository.ImageRepository;

import java.util.List;

public class ImageListViewModel extends ViewModel {

    private ImageRepository mImageRepository;

    public ImageListViewModel() {
        mImageRepository = ImageRepository.getInstance();
    }

    public LiveData<List<Image>> getImages() {
        return mImageRepository.getImages();
    }

    public void searchImagesApi(int pageNumber, String query) {
        mImageRepository.searchImagesApi(pageNumber, query);
    }
}
