package com.felix.imgurexplorer.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.felix.imgurexplorer.R;
import com.felix.imgurexplorer.model.Image;
import com.felix.imgurexplorer.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int IMAGE_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int EXHAUSTED_TYPE = 3;

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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;

        switch (i) {
            case IMAGE_TYPE: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_item, viewGroup, false);
                return new ImageViewHolder(view, mListener);
            }
            case LOADING_TYPE: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_loading_list_item, viewGroup, false);
                return new LoadingViewHolder(view);
            }
            case EXHAUSTED_TYPE: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_search_exhausted, viewGroup, false);
                return new SearchExhaustedViewHolder(view);
            }
            default: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_item, viewGroup, false);
                return new ImageViewHolder(view, mListener);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        int itemViewType = getItemViewType(i);
        Image image = mImageList.get(i);

        if (itemViewType == IMAGE_TYPE) {

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

            Glide.with(((ImageViewHolder) viewHolder).mImageView)
                    .setDefaultRequestOptions(options)
                    .load(imageUrl)
                    .into(((ImageViewHolder) viewHolder).mImageView);

            ((ImageViewHolder) viewHolder).mTextViewTitle.setText(image.getTitle());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mImageList.get(position).getTitle().equals("LOADING...")) {
            return LOADING_TYPE;
        } else if (mImageList.get(position).getTitle().equals("EXHAUSTED...")) {
            return EXHAUSTED_TYPE;
        } else if (position == mImageList.size() - 1
                && position != 0
                && !mImageList.get(position).getTitle().equals("EXHAUSTED...")) {
            return LOADING_TYPE;
        } else {
            return IMAGE_TYPE;
        }
    }

    public void setQueryExhausted() {
        hideLoading();
        Image exhaustedMarkerImage = new Image();
        exhaustedMarkerImage.setTitle("EXHAUSTED...");
        mImageList.add(exhaustedMarkerImage);
        notifyDataSetChanged();
    }

    public void hideLoading() {
        if (isLoading()) {
            for (Image image : mImageList) {
                if (image.getTitle().equals("LOADING...")) {
                    mImageList.remove(image);
                }
            }
            notifyDataSetChanged();
        }
    }

    public void displayLoading() {
        if (!isLoading()) {
            Image image = new Image();
            image.setTitle("LOADING...");
            List<Image> loadingList = new ArrayList<>();
            loadingList.add(image);
            mImageList = loadingList;
            notifyDataSetChanged();
        }
    }

    private boolean isLoading() {
        if (mImageList != null) {
            if (mImageList.size() > 0) {
                if (mImageList.get(mImageList.size() - 1).getTitle().equals("LOADING")) {
                    return true;
                }
            }
        }
        return false;
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

    public Image getSelectedImage(int position) {
        if (mImageList != null) {
            if (mImageList.size() > 0) {
                return mImageList.get(position);
            }
        }
        return null;
    }
}
