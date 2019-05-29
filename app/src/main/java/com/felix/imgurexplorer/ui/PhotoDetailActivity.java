package com.felix.imgurexplorer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.felix.imgurexplorer.R;
import com.felix.imgurexplorer.model.Photo;

import static com.felix.imgurexplorer.ui.MainActivity.MOVIE;

public class PhotoDetailActivity extends BaseActivity {

    private ImageView mIvPhotoDetail;
    private TextView mTvPhotoDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Photo photo = intent.getParcelableExtra(MOVIE);
        mIvPhotoDetail = findViewById(R.id.image_view_photo_detail);
        mTvPhotoDetail = findViewById(R.id.text_view_photo_detail);

        String imageUrl = "https://i.imgur.com/" + photo.getId() + ".jpg";
        Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(mIvPhotoDetail);

        this.setTitle("");
        mTvPhotoDetail.setText(photo.getTitle());
    }
}
