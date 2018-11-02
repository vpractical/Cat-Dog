package com.y.mvp.base;

import java.util.HashMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RxPresenter<T extends BaseView> implements BasePresenter<T> {

    protected T mView;
    private CompositeDisposable mCompositeDisposable;
    private HashMap<Object, Disposable> disposableMap;

    protected void addSubscribe(String tag,Disposable subscription){
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        if (disposableMap == null) {
            disposableMap = new HashMap<>();
        }
        //添加前先移除
        removeSubscribe(tag);
        //添加到队列
        mCompositeDisposable.add(subscription);
        //加入到管理
        disposableMap.put(tag, subscription);
    }

    /**
     * 为了防止重复请求，这里我们进行移除指定的订阅者
     *
     * @param tag 订阅者标记
     */
    private void removeSubscribe(String tag) {
        if (disposableMap != null) {
            //取出队列中订阅者进行主动消费掉
            Disposable disposable = disposableMap.get(tag);

            if (disposable != null) {
                //主动的进行消费
                disposable.dispose();
                //从管理集合中移除
                disposableMap.remove(tag);
            }
        }
    }


    private void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
            mCompositeDisposable = null;
        }
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }
}
