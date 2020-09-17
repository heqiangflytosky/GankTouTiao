package com.android.hq.ganktoutiao.ui.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hq.ganktoutiao.R;
import com.android.hq.ganktoutiao.data.GankItem;
import com.android.hq.ganktoutiao.data.GankSearchItem;
import com.android.hq.ganktoutiao.data.bean.GankSearchItemBean;
import com.android.hq.ganktoutiao.data.bean.SearchDataResponse;
import com.android.hq.ganktoutiao.network.CallBack;
import com.android.hq.ganktoutiao.network.RequestManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heqiang on 16-10-13.
 */
public class SearchFragment extends BaseFragment {
    private final static String TAG = "SearchFragment";
    private EditText mEditText;
    private ImageView mCleanSearchView;
    private LinearLayout mToolBarTop;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ViewStub viewStub = (ViewStub) rootView.findViewById(R.id.tool_bar_top_stub);
        viewStub.inflate();

        mToolBarTop = (LinearLayout) rootView.findViewById(R.id.tool_bar_top_layout);
        View searchBar = inflater.inflate(R.layout.search_bar, mToolBarTop);
        mEditText = (EditText) searchBar.findViewById(R.id.search_edit);
        mCleanSearchView = (ImageView) searchBar.findViewById(R.id.clean_text_img);

        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                Log.d(TAG, "submit = " + textView.getText().toString().trim() + ", actionId = " + actionId);
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    mEditText.clearFocus();
                    getSearchResult(textView.getText().toString().trim());
                }
                return false;
            }
        });
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    mCleanSearchView.setVisibility(View.VISIBLE);
                } else {
                    mCleanSearchView.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mCleanSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditText.setText("");
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void updateData() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public boolean isEnablePullRefresh() {
        return false;
    }

    @Override
    public boolean isEnableLoadingMore() {
        return false;
    }

    @Override
    public boolean isEnableRefreshOnViewCreate() {
        return false;
    }

    private void getSearchResult(String keyWord){
        mRefreshLayout.setRefreshing(true);
        RequestManager.getInstance().getSearchData(keyWord, 30, 1, new CallBack<SearchDataResponse>() {
            @Override
            public void onSuccess(SearchDataResponse gankDataResponse) {
                mRefreshLayout.setRefreshing(false);
                List<GankItem> list = new ArrayList<GankItem>();
                if(gankDataResponse != null){
                    ArrayList<GankSearchItemBean> dataList = gankDataResponse.results;
                    if(dataList != null && dataList.size() != 0){
                        for(GankSearchItemBean bean : dataList){
                            list.add(new GankSearchItem(bean));
                        }
                    }
                }
                mAdapter.updateData(list);
            }

            @Override
            public void onFail() {

            }
        });
    }
}
