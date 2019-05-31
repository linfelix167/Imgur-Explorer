package com.felix.imgurexplorer.request;

import com.felix.imgurexplorer.request.response.ImageSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ImageApi {

    @Headers("Authorization: Client-ID 126701cd8332f32")
    @GET("gallery/search/time/{page}")
    Call<ImageSearchResponse> searchPhoto(
            @Path("page") int page,
            @Query("q") String query
    );
}
