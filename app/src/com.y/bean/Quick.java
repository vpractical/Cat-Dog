package com.y.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.y.mvp.observer.CommonSubscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Quick implements MultiItemEntity{

    public static final int MULTI_TEXT = 0;
    public static final int MULTI_IMAGE = 1;
    public static final int MULTI_TEXT_IMAGE = 2;

    public int id;
    public String image;
    public String content;
    public int multiType;

    public static void getData(final int page, CommonSubscriber subscriber) {

        final List<Quick> data = new ArrayList<>();

        int size = 10;
        if (page == 5 || page == -4) {
            size = 1;
        }

        for (int i = 0; i < size; i++) {
            Quick quick = new Quick();
            quick.id = 10 * page - (page > 0 ? 10 : 0) + i;
            quick.image = "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3606733376,1982528758&fm=173&app=25&f=JPEG?w=640&h=360&s=8884CC130EB767A1432079E60300A022";
            quick.content = "樱花节";
            quick.multiType = i % 5 / 2;
            data.add(quick);
        }

        Flowable
                .create(new FlowableOnSubscribe<List<Quick>>() {
                    @Override
                    public void subscribe(FlowableEmitter<List<Quick>> emitter) throws Exception {
                        if(page == 3){
                            emitter.onError(new Throwable("------"));
                        }else{
                            emitter.onNext(data);
                        }
                    }
                }, BackpressureStrategy.BUFFER)
                .delay(2L, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    @Override
    public int getItemType() {
        return multiType;
    }
}
