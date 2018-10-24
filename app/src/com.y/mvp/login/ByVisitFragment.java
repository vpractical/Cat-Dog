package com.y.mvp.login;

import android.widget.ImageView;
import android.widget.TextView;

import com.y.R;
import com.y.imageloader.ImageLoader;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class ByVisitFragment extends BaseLoginFragment {

    public static ByVisitFragment newInstance() {
        ByVisitFragment byVisit = new ByVisitFragment();
        return byVisit;
    }

    private static final String TAG = "ByVisitFragment";

    @BindView(R.id.iv_visit_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_visit_nick)
    TextView tvNick;
    @BindView(R.id.tv_visit_phone)
    TextView tvPhone;
    @BindView(R.id.tv_visit_login)
    TextView tvLogin;

    private Disposable countDownDisposable;

    @Override
    protected int getLayout() {
        return R.layout.fragment_login_visit;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void init() {
        tvNick.setText("安尼恩");
        tvPhone.setText("18300000000");
    }

    @Override
    public void onStart() {
        super.onStart();
        couldShow();
    }

    @Override
    public void onStop() {
        super.onStop();
        couldHide();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        couldHide();
    }

    private void couldShow() {
        final String avatar = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=4034242562,3519494498&fm=27&gp=weather0.jpg";
        ImageLoader.with(this).url(avatar).circle().into(ivAvatar);

        countDownDisposable = Observable
                .intervalRange(0, 5, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long o) throws Exception {
                        tvLogin.setText(String.format("在此页面停留%1$ds后进入首页", 5 - o));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        mPresenter.loginByVisit(avatar,tvPhone.getText().toString(),tvNick.getText().toString());
                    }
                });
    }

    private void couldHide() {
        if (!countDownDisposable.isDisposed()) {
            countDownDisposable.dispose();
        }
    }

}
