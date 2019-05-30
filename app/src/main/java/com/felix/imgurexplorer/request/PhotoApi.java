package com.felix.imgurexplorer.request;

import com.felix.imgurexplorer.request.response.PhotoSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PhotoApi {

    @Headers("Authorization: Client-ID 126701cd8332f32")
    @GET("gallery/search/time/{page}")
    Call<PhotoSearchResponse> searchPhoto(
            @Path("page") int page,
            @Query("q") String query
    );
}
