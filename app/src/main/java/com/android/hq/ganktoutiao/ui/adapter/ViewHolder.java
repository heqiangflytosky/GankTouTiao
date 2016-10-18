package com.android.hq.ganktoutiao.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hq.ganktoutiao.R;
import com.android.hq.ganktoutiao.ui.view.RatioImageView;

/**
 * Created by heqiang on 16-10-9.
 */
public class ViewHolder {
    public static class ContentViewHolder extends RecyclerView.ViewHolder{
        public TextView mTitle;
        public TextView mFrom;
        public TextView mTime;

        public ContentViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_content, parent,false));
            mTitle = (TextView) itemView.findViewById(R.id.content_title);
            mFrom = (TextView) itemView.findViewById(R.id.item_footer_from);
            mTime = (TextView) itemView.findViewById(R.id.item_footer_time);
        }
    }

    public static class FooterHolder extends RecyclerView.ViewHolder{
        //public TextView mTitle;

        public FooterHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_footer, parent,false));
            //mTitle = (TextView) itemView.findViewById(R.id.content_title);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder{
        public TextView mTitle;

        public HeaderViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_title, parent,false));
            mTitle = (TextView) itemView.findViewById(R.id.category_title);
        }
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{
        public RatioImageView mImageView;

        public ImageViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_image, parent,false));
            mImageView = (RatioImageView) itemView.findViewById(R.id.image);
            mImageView.setRatio(1.5f);
        }
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder{
        public TextView mTitle;
        public TextView mFrom;
        public TextView mTime;
        public TextView mType;

        public SearchViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_content, parent,false));
            mTitle = (TextView) itemView.findViewById(R.id.content_title);
            mFrom = (TextView) itemView.findViewById(R.id.item_footer_from);
            mTime = (TextView) itemView.findViewById(R.id.item_footer_time);
            mType = (TextView) itemView.findViewById(R.id.item_footer_type);
            mType.setVisibility(View.VISIBLE);
        }
    }
}
