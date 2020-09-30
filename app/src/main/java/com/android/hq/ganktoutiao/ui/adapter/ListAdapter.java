package com.android.hq.ganktoutiao.ui.adapter;

import android.app.Dialog;
import android.app.Fragment;

import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.hq.ganktoutiao.R;
import com.android.hq.ganktoutiao.data.GankContentItem;
import com.android.hq.ganktoutiao.data.GankDetailData;
import com.android.hq.ganktoutiao.data.GankFooterItem;
import com.android.hq.ganktoutiao.data.GankGirlItem;
import com.android.hq.ganktoutiao.data.GankHeaderItem;
import com.android.hq.ganktoutiao.data.GankImageItem;
import com.android.hq.ganktoutiao.data.GankItem;
import com.android.hq.ganktoutiao.data.GankSearchItem;
import com.android.hq.ganktoutiao.data.GankType;
import com.android.hq.ganktoutiao.data.HistoryFavItem;
import com.android.hq.ganktoutiao.ui.view.WrapContentDraweeView;
import com.android.hq.ganktoutiao.utils.AppUtils;
import com.bumptech.glide.Glide;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.List;

/**
 * Created by heqiang on 16-9-26.
 */
public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_INVALID = -1;
    private static final int TYPE_CONTENT = 1;
    private static final int TYPE_IMAGE = 2;
    private static final int TYPE_HEADER = 3;
    private static final int TYPE_FOOTER = 4;
    private static final int TYPE_SEARCH_ITEM = 5;
    private static final int TYPE_HISTORY_FAV_ITEM = 6;
    private static final int TYPE_GIRL = 7;

    private Fragment mFragment;
    private List<GankItem> mList;

    public ListAdapter(Fragment fragment){
        mFragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_CONTENT:
                return new ViewHolder.ContentViewHolder(parent);
            case TYPE_IMAGE:
                return new ViewHolder.ImageViewHolder(parent);
            case TYPE_HEADER:
                return new ViewHolder.HeaderViewHolder(parent);
            case TYPE_FOOTER:
                return new ViewHolder.FooterHolder(parent);
            case TYPE_SEARCH_ITEM:
                return new ViewHolder.SearchViewHolder(parent);
            case TYPE_HISTORY_FAV_ITEM:
                return new ViewHolder.HistoryFavViewHolder(parent);
            case TYPE_GIRL:
                return new ViewHolder.GirlViewHolder(parent);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        GankItem item = mList.get(position);
        if(item instanceof GankGirlItem) {
            // GankGirlItem 要放在 GankContentItem 前面，因为 GankGirlItem 继承自 GankContentItem
            return TYPE_GIRL;
        } else if(item instanceof GankContentItem){
            return TYPE_CONTENT;
        }else if(item instanceof GankImageItem){
            return TYPE_IMAGE;
        }else if(item instanceof GankHeaderItem){
            return TYPE_HEADER;
        }else if(item instanceof GankFooterItem){
            return TYPE_FOOTER;
        }else if(item instanceof GankSearchItem){
            return TYPE_SEARCH_ITEM;
        }else if(item instanceof HistoryFavItem){
            return TYPE_HISTORY_FAV_ITEM;
        }
        return TYPE_INVALID;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder.ContentViewHolder){
            ViewHolder.ContentViewHolder contentViewHolder = (ViewHolder.ContentViewHolder) holder;
            final GankContentItem item = (GankContentItem) mList.get(position);
            contentViewHolder.mTitle.setText(item.title);
            contentViewHolder.mDesc.setText(item.desc);
            String who = item.author;
            if(!TextUtils.isEmpty(who)) {
                contentViewHolder.mFrom.setText(who);
            }
            String time = AppUtils.formatPublishedTime(item.publishedAt);
            if(!TextUtils.isEmpty(time)) {
                contentViewHolder.mTime.setText(time);
            }

            contentViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppUtils.startArticleDetailActivity(mFragment.getActivity(),
                            new GankDetailData(item._id, item.type, item.url, item.author, item.title,item.desc, item.publishedAt, System.currentTimeMillis()));
                }
            });
        }else if(holder instanceof ViewHolder.ImageViewHolder){
            ViewHolder.ImageViewHolder imageViewHolder = (ViewHolder.ImageViewHolder) holder;
            Glide.with(mFragment)
                    .load(((GankImageItem)mList.get(position)).imageUrl)
                    .centerCrop()
                    .into(imageViewHolder.mImageView);
        }else if(holder instanceof ViewHolder.HeaderViewHolder){
            ViewHolder.HeaderViewHolder headerViewHolder = (ViewHolder.HeaderViewHolder) holder;
            String title = ((GankHeaderItem)mList.get(position)).name;
            headerViewHolder.mTitle.setText(title);
            switch (title){
                case GankType.TYPE_BENEFIT:
                    AppUtils.setTextViewLeftDrawableForHeader(headerViewHolder.mTitle, MaterialDesignIconic.Icon.gmi_mood);
                    break;
                case GankType.TYPE_ANDROID:
                    AppUtils.setTextViewLeftDrawableForHeader(headerViewHolder.mTitle, MaterialDesignIconic.Icon.gmi_android);
                    break;
                case GankType.TYPE_IOS:
                    AppUtils.setTextViewLeftDrawableForHeader(headerViewHolder.mTitle, MaterialDesignIconic.Icon.gmi_apple);
                    break;
                case GankType.TYPE_APP:
                    AppUtils.setTextViewLeftDrawableForHeader(headerViewHolder.mTitle, MaterialDesignIconic.Icon.gmi_apps);
                    break;
                case GankType.TYPE_REST_VIDEO:
                    AppUtils.setTextViewLeftDrawableForHeader(headerViewHolder.mTitle, MaterialDesignIconic.Icon.gmi_collection_video);
                    break;
                case GankType.TYPE_EXPAND_RES:
                    AppUtils.setTextViewLeftDrawableForHeader(headerViewHolder.mTitle, FontAwesome.Icon.faw_location_arrow);
                    break;
                default:
                    AppUtils.setTextViewLeftDrawableForHeader(headerViewHolder.mTitle, MaterialDesignIconic.Icon.gmi_more);
                    break;
            }
        }else if(holder instanceof ViewHolder.SearchViewHolder){
            ViewHolder.SearchViewHolder contentViewHolder = (ViewHolder.SearchViewHolder) holder;
            final GankSearchItem item = (GankSearchItem) mList.get(position);
            contentViewHolder.mTitle.setText(item.title);
            contentViewHolder.mDesc.setText(item.desc);
            String who = item.author;
            if(!TextUtils.isEmpty(who)) {
                contentViewHolder.mFrom.setText(who);
            }
            String time = AppUtils.formatPublishedTime(item.publishedAt);
            if(!TextUtils.isEmpty(time)) {
                contentViewHolder.mTime.setText(time);
            }
            contentViewHolder.mType.setText(item.type);

            contentViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppUtils.startArticleDetailActivity(mFragment.getActivity(),
                            new GankDetailData(item.ganhuo_id, item.type, item.url, item.author, item.title, item.desc, item.publishedAt, System.currentTimeMillis()));
                }
            });
        }else if (holder instanceof ViewHolder.HistoryFavViewHolder){
            ViewHolder.HistoryFavViewHolder contentViewHolder = (ViewHolder.HistoryFavViewHolder) holder;
            final HistoryFavItem item = (HistoryFavItem) mList.get(position);
            contentViewHolder.mTitle.setText(item.title);
            contentViewHolder.mDesc.setText(item.desc);
            String who = item.who;
            if(!TextUtils.isEmpty(who)) {
                contentViewHolder.mFrom.setText(who);
            }
            String time = AppUtils.formatPublishedTime(item.published_date);
            if(!TextUtils.isEmpty(time)) {
                contentViewHolder.mTime.setText(time);
            }
            contentViewHolder.mType.setText(item.gank_type);

            contentViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppUtils.startArticleDetailActivity(mFragment.getActivity(),
                            new GankDetailData(item.gank_id, item.gank_type, item.url, item.who, item.title, item.desc, item.published_date, System.currentTimeMillis()));
                }
            });
        } else if (holder instanceof ViewHolder.GirlViewHolder) {
            ViewHolder.GirlViewHolder contentViewHolder = (ViewHolder.GirlViewHolder) holder;
            final GankGirlItem item = (GankGirlItem) mList.get(position);
            contentViewHolder.mImage.setCallback(new WrapContentDraweeView.Callback() {
                @Override
                public void updateRatio(float ratio) {
                    item.ratio = ratio;
                }
            });
            contentViewHolder.mImage.setImageURI(item.url);
            contentViewHolder.mImage.setAspectRatio(item.ratio);
            contentViewHolder.mTitle.setText(item.title);
            contentViewHolder.mDesc.setText(item.desc);
            contentViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppUtils.startImageBrowserActivity(mFragment.getActivity(), mList, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void updateData(List<GankItem> list){
        if(mList == null || !mList.containsAll(list)){
            forceUpdateData(list);
        }else{
            Toast.makeText(mFragment.getActivity(), R.string.text_update, Toast.LENGTH_SHORT).show();
        }
    }

    public void forceUpdateData(List<GankItem> list){
        mList = list;
        notifyDataSetChanged();
    }

    public void loadMoreData(List<GankItem> list){
        mList.remove(mList.size() - 1);
        notifyItemRangeRemoved(mList.size(),1);
        if(list != null) {
            int size = mList.size();
            mList.addAll(size,list);
            notifyItemRangeInserted(size,list.size());
        }
    }

    public void onLoadMore(){
        int size = mList.size();
        mList.add(new GankFooterItem());
        notifyItemRangeInserted(size,1);
    }
}
