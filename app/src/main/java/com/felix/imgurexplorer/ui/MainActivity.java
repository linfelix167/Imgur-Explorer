package com.felix.imgurexplorer.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.felix.imgurexplorer.R;
import com.felix.imgurexplorer.adapter.ImageAdapter;
import com.felix.imgurexplorer.model.Image;
import com.felix.imgurexplorer.viewmodel.ImageListViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements ImageAdapter.OnItemClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String MOVIE = "MOVIE";

    private ImageListViewModel mImageListViewModel;
    private RecyclerView mRecyclerView;
    private ImageAdapter mImageAdapter;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageListViewModel = ViewModelProviders.of(this).get(ImageListViewModel.class);

        mRecyclerView = findViewById(R.id.recycler_view);
        mSearchView = findViewById(R.id.search_view);

        initRecyclerView();
        subscribeObservers();
        testRetrofitRequest();
    }

    private void subscribeObservers() {
        mImageListViewModel.getImages().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(@Nullable List<Image> images) {
                if (images != null) {

                }
                mImageAdapter.setImages(images);
            }
        });
    }

    private void initRecyclerView() {
        mImageAdapter = new ImageAdapter(this);
        mRecyclerView.setAdapter(mImageAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void testRetrofitRequest() {
        mImageListViewModel.searchImagesApi(1, "cats");
    }

    private void initSearchView() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public void onItemClick(int position) {
//        Intent intent = new Intent(this, ImageDetailActivity.class);
//        Image clickedImage = mImageList.get(position);
//        intent.putExtra(MOVIE, clickedImage);
//        startActivity(intent);
    }
}
