package com.y.mvp.login;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;

import com.y.R;
import com.y.bean.Login;
import com.y.config.Key;
import com.y.util.SPUtil;
import com.y.util.T;

import butterknife.BindView;
import butterknife.OnClick;

public class ByPwdFragment extends BaseLoginFragment{

    @BindView(R.id.et_login_account)
    EditText etLoginAccount;
    @BindView(R.id.input_login_account)
    TextInputLayout inputLoginAccount;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.input_login_pwd)
    TextInputLayout inputLoginPwd;
    @BindView(R.id.btn_login_pwd_submit)
    Button btnSubmit;
    @BindView(R.id.btn_login_pwd_forget)
    Button btnForget;
    @BindView(R.id.btn_login_pwd_register)
    Button btnRegister;
    @BindView(R.id.tv_check)
    CheckedTextView tvCheck;

    public static ByPwdFragment newInstance() {
        ByPwdFragment byPwd = new ByPwdFragment();
        return byPwd;
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_login_pwd;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void init() {
        Login login = (Login) SPUtil.getSingleObject(Key.LOGIN_KEY,Login.class);
        if(login != null && login.account != null){
            etLoginAccount.setText(login.account);
        }

        String pwd = SPUtil.getCommonString(Key.LOGIN_PASSWORD);
        if(pwd != null){
            etLoginPwd.setText(pwd);
        }

        initListener();
    }

    private void initListener() {
        tvCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCheck.toggle();
                if(!tvCheck.isChecked()){
                    SPUtil.deleteCommonString(Key.LOGIN_PASSWORD);
                }
            }
        });

        etLoginAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() > 11){
                    inputLoginAccount.setErrorEnabled(true);
                    inputLoginAccount.setError("账号长度过长");
                }else{
                    inputLoginAccount.setErrorEnabled(false);
                }
            }
        });
        etLoginPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() > 11){
                    inputLoginPwd.setErrorEnabled(true);
                    inputLoginPwd.setError("密码长度过长");
                }else{
                    inputLoginPwd.setErrorEnabled(false);
                }
            }
        });
    }


    @OnClick({R.id.btn_login_pwd_submit, R.id.btn_login_pwd_forget, R.id.btn_login_pwd_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login_pwd_submit:
                login();
                break;
            case R.id.btn_login_pwd_forget:
                forget();
                break;
            case R.id.btn_login_pwd_register:
                register();
                break;
        }
    }

    private void save(String pwd){
        if(tvCheck.isChecked()){
            SPUtil.putCommonString(Key.LOGIN_PASSWORD,pwd);
        }
    }

    private void forget() {
    }

    private void register() {
        String account = etLoginAccount.getText().toString();
        String pwd = etLoginPwd.getText().toString();
        if(TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)){
            T.show("账号密码格式不正确");
            return;
        }
        save(pwd);
        mPresenter.registerPwd(account,pwd);
    }

    private void login() {
        String account = etLoginAccount.getText().toString();
        String pwd = etLoginPwd.getText().toString();
        if(TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)){
            T.show("账号密码格式不正确");
            return;
        }
        save(pwd);
        mPresenter.loginByPwd(account,pwd);
    }

}
