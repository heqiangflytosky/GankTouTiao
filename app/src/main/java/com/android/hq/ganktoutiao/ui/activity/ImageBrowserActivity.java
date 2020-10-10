package com.android.hq.ganktoutiao.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.SharedElementCallback;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.hq.ganktoutiao.R;
import com.android.hq.ganktoutiao.data.GankGirlItem;
import com.android.hq.ganktoutiao.data.GankItem;
import com.android.hq.ganktoutiao.mvp.GankListContract;
import com.android.hq.ganktoutiao.mvp.GirlDataSource;
import com.android.hq.ganktoutiao.mvp.GirlListPresenter;
import com.android.hq.ganktoutiao.ui.view.WrapContentDraweeView;
import com.android.hq.ganktoutiao.utils.Event;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

public class ImageBrowserActivity extends AppCompatActivity implements GankListContract.View {

    public static final String EXTRA_DATA = "EXTRA_DATA";
    public static final String EXTRA_INDEX = "EXTRA_INDEX";
    private ViewPager2 mViewPager;
    private Adapter mAdapter;
    private TextView mIndexText;
    private List<GankItem> mList;
    private int mInitIndex;
    private GirlListPresenter mGirlListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 解决SimpleDraweeView无法共享元素动画的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.CENTER_CROP));
            getWindow().setSharedElementReturnTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.CENTER_CROP));
        }
        supportPostponeEnterTransition();

        setContentView(R.layout.activity_image_browser);
        setFullScreen();
        mInitIndex = getIntent().getIntExtra(EXTRA_INDEX, 0);
        init();
        new GirlListPresenter(this, GirlDataSource.getInstance());
        loadData();
        //mList = (List<GankItem>)getIntent().getSerializableExtra(EXTRA_DATA);
        //setData(mList);
        //mViewPager.setCurrentItem(index, false);
        mViewPager.registerOnPageChangeCallback(mPageChangeCallback);

        //mIndexText.setText(index+"/"+mList.size());

        //监听viewpager布局树已经绘制完成
        mViewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mViewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //开始共享元素动画
                supportStartPostponedEnterTransition();
            }
        });
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                names.clear();
                names.add(String.valueOf(mViewPager.getCurrentItem()));
                sharedElements.clear();
                sharedElements.put(String.valueOf(mViewPager.getCurrentItem()),
                        mViewPager.findViewWithTag(mViewPager.getCurrentItem()));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 解决全屏切换非全屏界面跳动问题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        Event event = new Event();
        event.msg = "Test Event";
        event.currentIndex = mViewPager.getCurrentItem();
        event.list = mList;
        EventBus.getDefault().post(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.unregisterOnPageChangeCallback(mPageChangeCallback);
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
        mIndexText = findViewById(R.id.index);
    }

    private void setData(List<GankItem> list) {
        mList = list;
        mAdapter.notifyDataSetChanged();
    }

    private void loadData() {
        mGirlListPresenter.loadData(null);
    }

    private ViewPager2.OnPageChangeCallback mPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
//            Event event = new Event();
//            event.msg = "Test Event";
//            event.currentIndex = mViewPager.getCurrentItem();
//            event.list = mList;
//            EventBus.getDefault().post(event);
            mIndexText.setText(position+"/"+mList.size());
        }
    };

    @Override
    public void setRefreshing(boolean refreshing) {

    }

    @Override
    public void updateData(List<GankItem> list) {
        mList = list;
        setData(mList);
        mViewPager.setCurrentItem(mInitIndex, false);

        mIndexText.setText(mInitIndex+"/"+mList.size());
    }

    @Override
    public void updateMoreData(List<GankItem> list) {

    }

    @Override
    public void updateSuccess(boolean isEmpty) {

    }

    @Override
    public void updateError() {

    }

    @Override
    public void updateLoadMoreError() {

    }

    @Override
    public void setPresenter(GankListContract.Presenter presenter) {
        mGirlListPresenter = (GirlListPresenter) presenter;
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
            contentViewHolder.image.setTag(position);
            ViewCompat.setTransitionName(contentViewHolder.image, String.valueOf(position));
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
