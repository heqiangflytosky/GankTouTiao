package com.android.hq.ganktoutiao.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.android.hq.ganktoutiao.R;
import com.android.hq.ganktoutiao.data.GankGirlItem;
import com.android.hq.ganktoutiao.data.GankItem;
import com.android.hq.ganktoutiao.ui.view.WrapContentDraweeView;

import java.util.List;

public class ImageBrowserActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "EXTRA_DATA";
    public static final String EXTRA_INDEX = "EXTRA_INDEX";
    private ViewPager2 mViewPager;
    private Adapter mAdapter;
    private List<GankItem> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_browser);
        setFullScreen();
        init();
        mList = (List<GankItem>)getIntent().getSerializableExtra(EXTRA_DATA);
        int index = getIntent().getIntExtra(EXTRA_INDEX, 0);
        setData(mList);
        mViewPager.setCurrentItem(index, false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 解决全屏切换非全屏界面跳动问题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }

    private void setFullScreen() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void init() {
        mViewPager = findViewById(R.id.view_pager);
        mAdapter = new Adapter();
        mViewPager.setAdapter(mAdapter);
    }

    public void setData(List<GankItem> list) {
        mList = list;
        mAdapter.notifyDataSetChanged();
    }

    public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            final GankGirlItem item = (GankGirlItem) mList.get(position);
            ViewHolder contentViewHolder = (ViewHolder) holder;
            contentViewHolder.image.setImageURI(item.url);
            contentViewHolder.image.setAspectRatio(item.ratio);
            contentViewHolder.image.setCallback(new WrapContentDraweeView.Callback() {
                @Override
                public void updateRatio(float ratio) {
                    item.ratio = ratio;
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList != null ? mList.size() : 0;
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        public WrapContentDraweeView image;
        public ViewHolder(@NonNull ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_item_image, parent, false));
            image = itemView.findViewById(R.id.content_image);
        }
    }
}
