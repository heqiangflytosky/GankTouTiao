package com.android.hq.ganktoutiao.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.hq.ganktoutiao.R;
import com.android.hq.ganktoutiao.data.GankDetailData;
import com.android.hq.ganktoutiao.data.bean.GankItemBean;
import com.android.hq.ganktoutiao.provider.GankProviderHelper;
import com.android.hq.ganktoutiao.utils.AppUtils;
import com.android.hq.ganktoutiao.utils.BackgroundHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by heqiang on 16-10-11.
 */
public class ArticleDetailActivity extends Activity{
    private Unbinder mUnbinder;

    @BindView(R.id.webview)
    WebView mWebView;
    private GankDetailData mData;

    @BindView(R.id.menu_back)
    ImageView mBack;
    @BindView(R.id.menu_more)
    ImageView mMore;
    @BindView(R.id.favorite)
    ImageView mFavorite;
    @BindView(R.id.share)
    ImageView mShare;
    @BindView(R.id.menu_title)
    TextView mTitle;
    @BindView(R.id.presenter)
    TextView mPresenter;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private boolean isFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        mUnbinder = ButterKnife.bind(this);

        initData();
        initViews();

    }

    private void initData(){
        Intent intent = getIntent();
        mData = (GankDetailData) intent.getSerializableExtra(AppUtils.INTENT_ITEM_INFO);
    }

    private void initViews(){
        mTitle.setText(mData.title);
        mPresenter.setText(getResources().getString(R.string.text_presenter) + mData.who);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(mWebChromeClient);
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.loadUrl(mData.url);

        uppdateFavStatus();
    }

    private void uppdateFavStatus(){
        BackgroundHandler.execute(new Runnable() {
            @Override
            public void run() {
                long id = GankProviderHelper.getInstance().queryFavouriteEntry(mData.gank_id, mData.gank_type);
                final boolean isFav = id < 0 ? false : true;
                if(!isDestroyed()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setFavorite(isFav);
                        }
                    });
                }
            }
        });
    }

    private void setFavorite(boolean favorite){
        isFavourite = favorite;
        if(favorite) {
            mFavorite.setImageResource(R.drawable.ic_action_favorite);
        } else {
            mFavorite.setImageResource(R.drawable.ic_action_favorite_outline_gray);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mWebView != null){
            if(mWebView.getParent() != null){
                ((ViewGroup)mWebView.getParent()).removeView(mWebView);
            }
            mWebView.destroy();
        }
        mUnbinder.unbind();
    }

    @OnClick({R.id.menu_back, R.id.menu_more, R.id.favorite, R.id.share})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.menu_back:
                finish();
                break;
            case R.id.menu_more:
                final ListPopupWindow listPopupWindow = new ListPopupWindow(this);
                listPopupWindow.setAdapter(new ArrayAdapter<>(this, R.layout.abc_popup_menu_item_layout,
                        getResources().getStringArray(R.array.article_detail_menu_more)));
                listPopupWindow.setAnchorView(view);
                listPopupWindow.setWidth(getResources().getDimensionPixelOffset(R.dimen.menu_more_pop_width));
                //listPopupWindow.setHorizontalOffset(0 - getResources().getDimensionPixelOffset(R.dimen.menu_more_pop_offset_right));
                listPopupWindow.setModal(true);
                listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        switch (position){
                            case 0:
                                mWebView.clearView();
                                mWebView.reload();
                                break;
                            case 1:
                                AppUtils.copy(ArticleDetailActivity.this,mData.url);
                                break;
                            case 2:
                                AppUtils.startBrowser(ArticleDetailActivity.this,mData.url);
                                break;
                            default:
                                break;
                        }
                        listPopupWindow.dismiss();

                    }
                });
                listPopupWindow.show();
                break;
            case R.id.favorite:
                BackgroundHandler.execute(new Runnable() {
                    @Override
                    public void run() {
                        if(isFavourite){
                            boolean success = GankProviderHelper.getInstance().deleteFavouriteEntry(mData.gank_id, mData.gank_type);
                            if (success){
                                if(!isDestroyed()){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            setFavorite(false);
                                        }
                                    });
                                }
                            }
                        }else{
                            boolean success = GankProviderHelper.getInstance().saveFavouriteEntry(mData);
                            if(success){
                                if(!isDestroyed()){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            setFavorite(true);
                                        }
                                    });
                                }
                            }
                        }
                    }
                });
                break;
            case R.id.share:
                AppUtils.share(this, mData.title, mData.url);
                break;
            default:

                break;
        }
    }

    private GankWebChromeClient mWebChromeClient = new GankWebChromeClient();
    private class GankWebChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            mProgressBar.setProgress(newProgress);
            if(newProgress == 100){
                mProgressBar.setVisibility(View.GONE);
            }
        }
    }

    private GankWebViewClient mWebViewClient = new GankWebViewClient();
    private class GankWebViewClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
