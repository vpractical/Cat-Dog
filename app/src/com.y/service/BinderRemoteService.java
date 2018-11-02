package com.y.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.y.util.AppUtil;
import com.y.util.L;

public class BinderRemoteService extends Service {

    private static final String DESC = "BinderRemoteService";
    private static final int ADD = IBinder.FIRST_CALL_TRANSACTION + 1;

    // 步骤1：创建Binder对象
    // 步骤2：创建 IInterface 接口类 的匿名类
    // 创建前，需要预先定义 继承了IInterface 接口的接口
    private Binder mBinder = new Stub();

    private IInterface mPlus = new IPlus() {
        @Override
        public int add(int a, int b) {
            return a + b;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        L.e("BinderRemoteService", AppUtil.processName());
        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private void init() {

        // 1. 将（add two int，plus）作为（key,value）对存入到Binder对象中的一个Map<String,IInterface>对象中
        // 2. 之后，Binder对象 可根据add two int通过queryLocalIInterface（）获得对应IInterface对象（即plus）的引用，可依靠该引用完成对请求方法的调用

        mBinder.attachInterface(mPlus,DESC);
    }

    public int add(int a, int b) {
        return a + b;
    }

    public class Stub extends Binder {
        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {

            switch (code) {
                case ADD:
                    L.e(AppUtil.processName() + " : ADD()");
                    data.enforceInterface(DESC);
                    int a = data.readInt();
                    int b = data.readInt();
                    int res = add(a, b);
                    reply.writeNoException();
                    reply.writeInt(res);
                    return true;
            }

            return super.onTransact(code, data, reply, flags);
        }

    }


    /**
     * IInterface接口实现类
     */
    public interface IPlus extends IInterface {
        //定义需要实现的接口方法，即Client进程需要调用的方法
        int add(int a, int b);
    }

//    public class Binder implements IBinder{
//        // Binder机制在Android中的实现主要依靠的是Binder类，其实现了IBinder接口
//        // IBinder接口：定义了远程操作对象的基本接口，代表了一种跨进程传输的能力
//        // 系统会为每个实现了IBinder接口的对象提供跨进程传输能力
//        // 即Binder类对象具备了跨进程传输的能力
//
//        void attachInterface(IInterface plus, String descriptor)；
//        // 1. 将（descriptor，plus）作为（key,value）对存入到Binder对象中的一个Map<String,IInterface>对象中
//        // 2. 之后，Binder对象 可根据descriptor通过queryLocalIInterface（）获得对应IInterface对象（即plus）的引用，可依靠该引用完成对请求方法的调用
//
//        IInterface queryLocalInterface(Stringdescriptor) ；
//        // 作用：根据 参数 descriptor 查找相应的IInterface对象（即plus引用）
//
//        boolean onTransact(int code, Parcel data, Parcel reply, int flags)；
//        // 定义：继承自IBinder接口的
//        // 作用：执行Client进程所请求的目标方法（子类需要复写）
//        // 参数说明：
//        // code：Client进程请求方法标识符。即Server进程根据该标识确定所请求的目标方法
//        // data：目标方法的参数。（Client进程传进来的，此处就是整数a和b）
//        // reply：目标方法执行后的结果（返回给Client进程）
//        // 注：运行在Server进程的Binder线程池中；当Client进程发起远程请求时，远程请求会要求系统底层执行回调该方法
//
//        final class BinderProxy implements IBinder {
//            // 即Server进程创建的Binder对象的代理对象类
//            // 该类属于Binder的内部类
//        }
//    }
}
