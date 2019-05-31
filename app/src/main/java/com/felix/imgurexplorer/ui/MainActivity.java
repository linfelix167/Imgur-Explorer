package com.felix.imgurexplorer.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.felix.imgurexplorer.R;
import com.felix.imgurexplorer.adapter.ImageAdapter;
import com.felix.imgurexplorer.model.Image;
import com.felix.imgurexplorer.viewmodel.ImageListViewModel;

import java.util.List;

public class MainActivity extends BaseActivity implements ImageAdapter.OnItemClickListener {

    public static final String IMAGE_EXTRA = "IMAGE_EXTRA";

    private ImageListViewModel mImageListViewModel;
    private RecyclerView mRecyclerView;
    private SearchView mSearchView;
    private ImageAdapter mImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageListViewModel = ViewModelProviders.of(this).get(ImageListViewModel.class);

        mRecyclerView = findViewById(R.id.recycler_view);
        mSearchView = findViewById(R.id.search_view);

        initRecyclerView();
        subscribeObservers();
        initSearchView();
    }

    private void subscribeObservers() {
        mImageListViewModel.getImages().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(@Nullable List<Image> images) {
                if (images != null) {
                    mImageListViewModel.setIsPerformingQuery(false);
                    mImageAdapter.setImages(images);
                }
            }
        });

        mImageListViewModel.isQueryExhausted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    mImageAdapter.setQueryExhausted();
                }
            }
        });
    }

    private void initRecyclerView() {
        mImageAdapter = new ImageAdapter(this);
        mRecyclerView.setAdapter(mImageAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!mRecyclerView.canScrollVertically(1)) {
                    // search the next page
                    mImageListViewModel.searchNextPage();
                }
            }
        });
    }

    private void initSearchView() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mImageAdapter.displayLoading();
                mImageListViewModel.searchImagesApi(0, query);
                mSearchView.clearFocus();
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
        Intent intent = new Intent(this, ImageDetailActivity.class);
        intent.putExtra(IMAGE_EXTRA, mImageAdapter.getSelectedImage(position));
        startActivity(intent);
    }
}
