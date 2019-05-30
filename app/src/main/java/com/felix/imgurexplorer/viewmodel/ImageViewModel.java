package com.felix.imgurexplorer.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.felix.imgurexplorer.model.Image;
import com.felix.imgurexplorer.repository.ImageRepository;

public class ImageViewModel extends AndroidViewModel {

    private MutableLiveData<Image> mImage = new MutableLiveData<>();
    private ImageRepository mImageRepository;

    public ImageViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Image> getImage() {
        return mImage;
    }
}
