package com.felix.imgurexplorer.ui;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.felix.imgurexplorer.R;
import com.felix.imgurexplorer.model.Image;
import com.felix.imgurexplorer.util.Constants;

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

        showProgressBar(true);
        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra(IMAGE_EXTRA)) {
            Image image = getIntent().getParcelableExtra(IMAGE_EXTRA);

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(Constants.BASE_IMAGE_URL + image.getId() + ".jpg")
                    .into(mIvPhotoDetail);

            mTvPhotoDetail.setText(image.getTitle());

            showParent();
            showProgressBar(false);
        }
    }

    private void showParent() {
        mParent.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
