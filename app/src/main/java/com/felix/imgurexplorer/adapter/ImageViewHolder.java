package com.felix.imgurexplorer.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.felix.imgurexplorer.R;

public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    AppCompatImageView mImageView;
    TextView mTextViewTitle;
    ImageAdapter.OnItemClickListener mOnItemClickListener;

    public ImageViewHolder(@NonNull View itemView, ImageAdapter.OnItemClickListener onItemClickListener) {
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
