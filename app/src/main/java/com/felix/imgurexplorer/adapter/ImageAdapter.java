package com.felix.imgurexplorer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.felix.imgurexplorer.R;
import com.felix.imgurexplorer.model.Image;
import com.felix.imgurexplorer.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private List<Image> mImageList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ImageAdapter(OnItemClickListener onItemClickListener) {
        mListener = onItemClickListener;
        mImageList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ImageViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {

        Image image = mImageList.get(i);

        imageViewHolder.mTextViewTitle.setText(image.getTitle());

        if (image.isIs_album()) {
            image.setId(image.getCover());
        } else {
            image.setId(image.getId());
        }

        String imageUrl = Constants.BASE_IMAGE_URL + image.getId() + ".jpg";

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);

        Glide.with(imageViewHolder.itemView)
                .setDefaultRequestOptions(options)
                .load(imageUrl)
                .into(imageViewHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        if (null == mImageList) return 0;
        return mImageList.size();
    }

    public void setImages(List<Image> images) {
        mImageList = images;
        notifyDataSetChanged();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        AppCompatImageView mImageView;
        TextView mTextViewTitle;
        OnItemClickListener mOnItemClickListener;

        public ImageViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewTitle = itemView.findViewById(R.id.text_view_title);
            this.mOnItemClickListener = onItemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}
