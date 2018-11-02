package com.y.mvp.login;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.y.R;
import com.y.util.L;
import com.y.util.T;
import com.y.util.ValidateUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ByCodeFragment extends BaseLoginFragment {

    public static ByCodeFragment newInstance(){
        ByCodeFragment byCode = new ByCodeFragment();
        return byCode;
    }

    @BindView(R.id.et_login_phone)
    EditText etPhone;
    @BindView(R.id.input_login_phone)
    TextInputLayout inputPhone;
    @BindView(R.id.et_login_code)
    EditText etCode;
    @BindView(R.id.input_login_code)
    TextInputLayout inputCode;
    @BindView(R.id.btn_login_code_send)
    Button btnSend;

    @Override
    protected int getLayout() {
        return R.layout.fragment_login_code;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void init() {

        initListener();
    }

    private void initListener(){
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!ValidateUtil.isMobileNO(s.toString())){
                    inputPhone.setErrorEnabled(true);
                    inputPhone.setError("请输入正确的手机号");
                }else{
                    inputPhone.setErrorEnabled(false);
                }
            }
        });

        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() != 4){
                    inputCode.setErrorEnabled(true);
                    inputCode.setError("请输入4位验证码");
                }else{
                    inputCode.setErrorEnabled(false);
                    login();
                }
            }
        });
    }

    private void login(){

        String phone = etPhone.getText().toString();
        String code = etCode.getText().toString();

        if(!check(phone)) {
            T.show("请检查输入的手机号.");
            return;
        }

        mPresenter.loginByCode(phone,code);
    }

    private boolean check(String phone){

        if(ValidateUtil.isMobileNO(phone)){
            return true;
        }
        return false;
    }


    @OnClick(R.id.btn_login_code_send)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn_login_code_send:
                L.e("发送验证码");
                break;
        }
    }
}
