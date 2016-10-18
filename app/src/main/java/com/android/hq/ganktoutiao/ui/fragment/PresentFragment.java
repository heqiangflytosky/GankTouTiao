package com.android.hq.ganktoutiao.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hq.ganktoutiao.R;
import com.android.hq.ganktoutiao.data.bean.AddToGankResponse;
import com.android.hq.ganktoutiao.network.CallBack;
import com.android.hq.ganktoutiao.network.RequestManager;

import org.w3c.dom.Text;

/**
 * Created by heqiang on 16-10-13.
 */
public class PresentFragment extends Fragment {

    private TextInputLayout mLayoutAddress;
    private TextInputLayout mLayoutTitle;
    private TextInputLayout mLayoutPresenterID;

    private EditText mEditTextAddress;
    private EditText mEditTextTitle;
    private EditText mEditTextPresentID;

    private TextView mSubmit;

    private Spinner mPresentTypeSpinner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initViews(inflater);
    }

    private View initViews(LayoutInflater inflater){
        View rootView = inflater.inflate(R.layout.fragment_present, null);
        mLayoutAddress = (TextInputLayout) rootView.findViewById(R.id.layout_address);
        mLayoutAddress.setHint(getResources().getString(R.string.title_address));
        mLayoutAddress.setHintAnimationEnabled(true);
        mLayoutAddress.setErrorEnabled(true);
        mLayoutAddress.setError("");

        mLayoutTitle = (TextInputLayout) rootView.findViewById(R.id.layout_title);
        mLayoutTitle.setHint(getResources().getString(R.string.title_title));
        mLayoutTitle.setHintAnimationEnabled(true);
        mLayoutTitle.setErrorEnabled(true);
        mLayoutTitle.setError("");

        mLayoutPresenterID = (TextInputLayout) rootView.findViewById(R.id.layout_present_id);
        mLayoutPresenterID.setHint(getResources().getString(R.string.title_presenter_id));
        mLayoutPresenterID.setHintAnimationEnabled(true);
        mLayoutPresenterID.setErrorEnabled(true);
        mLayoutPresenterID.setError("");

        mEditTextAddress = (EditText) rootView.findViewById(R.id.edit_address);
        mEditTextAddress.addTextChangedListener(mTextWatcher);

        mEditTextTitle = (EditText) rootView.findViewById(R.id.edit_title);
        mEditTextTitle.addTextChangedListener(mTextWatcher);

        mEditTextPresentID = (EditText) rootView.findViewById(R.id.edit_id);
        mEditTextPresentID.addTextChangedListener(mTextWatcher);

        mPresentTypeSpinner = (Spinner) rootView.findViewById(R.id.type_spinner);

        mSubmit = (TextView) rootView.findViewById(R.id.bt_submit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = mEditTextAddress.getText() != null ? mEditTextAddress.getText().toString() : "";
                String title = mEditTextTitle.getText() != null ? mEditTextTitle.getText().toString() : "";
                String id = mEditTextPresentID.getText() != null ? mEditTextPresentID.getText().toString() : "";
                String type = mPresentTypeSpinner.getSelectedItem().toString();
                RequestManager.getInstance().add2Gank(address, title, id, type, new CallBack<AddToGankResponse>() {
                    @Override
                    public void onSuccess(AddToGankResponse addToGankResponse) {
                        if(addToGankResponse != null){
                            if(!addToGankResponse.error){
                                Toast.makeText(getActivity(),getResources().getString(R.string.tips_upload_success), Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getActivity(),addToGankResponse.msg, Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getActivity(),getResources().getString(R.string.tips_upload_failed), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(getActivity(),getResources().getString(R.string.tips_upload_failed), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        updateSubmitBtState();
        return rootView;
    }

    private void updateSubmitBtState(){
        String address = mEditTextAddress.getText() != null ? mEditTextAddress.getText().toString() : "";
        String title = mEditTextTitle.getText() != null ? mEditTextTitle.getText().toString() : "";
        String id = mEditTextPresentID.getText() != null ? mEditTextPresentID.getText().toString() : "";
        if(!TextUtils.isEmpty(address) && !TextUtils.isEmpty(title) && !TextUtils.isEmpty(id) && Patterns.WEB_URL.matcher(address).matches()){
            mSubmit.setEnabled(true);
        }else{
            mSubmit.setEnabled(false);
        }

    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            updateSubmitBtState();
        }
    };
}
