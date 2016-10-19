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
import com.android.hq.ganktoutiao.utils.AppUtils;

/**
 * Created by heqiang on 16-10-11.
 */
public class ArticleDetailActivity extends Activity implements View.OnClickListener{
    private WebView mWebView;
    private GankDetailData mData;

    private ImageView mBack;
    private ImageView mMore;
    private ImageView mFavorite;
    private ImageView mShare;
    private TextView mTitle;
    private TextView mPresenter;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        initData();
        initViews();

    }

    private void initData(){
        Intent intent = getIntent();
        mData = (GankDetailData) intent.getSerializableExtra(AppUtils.INTENT_ITEM_INFO);
    }

    private void initViews(){
        mBack = (ImageView) findViewById(R.id.menu_back);
        mMore = (ImageView) findViewById(R.id.menu_more);
        mTitle = (TextView) findViewById(R.id.menu_title);
        mWebView = (WebView) findViewById(R.id.webview);
        mPresenter = (TextView) findViewById(R.id.presenter);
        mFavorite = (ImageView) findViewById(R.id.favorite);
        mShare = (ImageView) findViewById(R.id.share);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mTitle.setText(mData.title);
        mBack.setOnClickListener(this);
        mMore.setOnClickListener(this);
        mFavorite.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mPresenter.setText(getResources().getString(R.string.text_presenter) + mData.who);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(mWebChromeClient);
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.loadUrl(mData.url);
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
    }

    @Override
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
            Log.e("Test", "newProgress = " + newProgress);
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
