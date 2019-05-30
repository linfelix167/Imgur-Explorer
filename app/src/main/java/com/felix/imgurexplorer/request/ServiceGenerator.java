package com.felix.imgurexplorer.request;

import com.felix.imgurexplorer.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static PhotoApi photoApi = retrofit.create(PhotoApi.class);

    public static PhotoApi getPhotoApi() {
        return photoApi;
    }
}
