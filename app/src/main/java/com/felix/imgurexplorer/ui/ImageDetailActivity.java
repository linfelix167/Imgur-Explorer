package com.felix.imgurexplorer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.felix.imgurexplorer.R;
import com.felix.imgurexplorer.model.Image;

import static com.felix.imgurexplorer.ui.MainActivity.IMAGE_EXTRA;

public class ImageDetailActivity extends BaseActivity {

    private AppCompatImageView mIvPhotoDetail;
    private TextView mTvPhotoDetail;
    private CoordinatorLayout mParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("");

        mParent = findViewById(R.id.parent);
        mIvPhotoDetail = findViewById(R.id.image_view_photo_detail);
        mTvPhotoDetail = findViewById(R.id.text_view_photo_detail);

        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra(IMAGE_EXTRA)) {
            Image image = getIntent().getParcelableExtra(IMAGE_EXTRA);

            mTvPhotoDetail.setText(image.getTitle());


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
