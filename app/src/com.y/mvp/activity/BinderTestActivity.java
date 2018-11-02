package com.y.mvp.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.y.service.BinderRemoteService;
import com.y.service.BinderService;
import com.y.util.AppUtil;
import com.y.util.L;
import com.y.util.T;

public class BinderTestActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, BinderTestActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private static final String TAG = "BinderTestActivity";
    private static final String DESC = "BinderRemoteService";
    private static final int ADD = IBinder.FIRST_CALL_TRANSACTION + 1;

    private BinderService service1;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private int num;

    /**
     * 1.绑定当前进程service
     * 2.绑定远程service
     */

    private IBinder mIBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.e("BinderTestActivity", AppUtil.processName());

        // 1
//        Intent serviceIntent = new Intent(this, BinderService.class);
//        bindService(serviceIntent, conn1, Context.BIND_AUTO_CREATE);

        // 2
        try{
            Intent serviceIntent = new Intent(this, BinderRemoteService.class);
            startService(serviceIntent);

            Intent componentIntent = new Intent();
            componentIntent.setComponent(new ComponentName("com.y.catdog", "com.y.service.BinderRemoteService"));
            bindService(componentIntent, conn2, Context.BIND_AUTO_CREATE);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void compute2() {
        if (mIBinder == null) {
            return;
        }

        if (num > 20) {
            try{
                unbindService(conn2);
            }catch(Exception e){
                e.printStackTrace();
            }
            mIBinder = null;
            return;
        }

//        理解：binder同进程的服务时，不用经过IBinder.transact()共享内核空间中转，直接强转为service中继承IBinder的对象调用方法，
//        binder不同进程服务时，不能强转，因为获得的内核空间binder不是binder本身对象，是其代理对象BinderProxy.
//        理解：IBinder.queryLocalInterface()获取远程service定义的接口对象，也只能获取本进程的，远程时返回null.

//        BinderRemoteService.IPlus iInterface = (BinderRemoteService.IPlus) mIBinder.queryLocalInterface(BinderRemoteService.DESC);
//        if(iInterface != null){
//            int res = iInterface.add(num, num++);
//            L.e("compute : " + res);
//            T.show("compute : " + res);
//        }
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken(DESC);
            data.writeInt(num);
            data.writeInt(num++);
            mIBinder.transact(ADD, data, reply, 0);
            reply.readException();
            int res = reply.readInt();
            L.e(AppUtil.processName() + " compute : " + res);
            T.show(AppUtil.processName() + " compute : " + res);
        } catch (RemoteException e) {
            e.printStackTrace();
        }finally {
            data.recycle();
            reply.recycle();
        }
        /*code：方法标识符，因为Client端对Server端的所有调用都会走到Server端的这个方法，所以理所应当Client端应该传递一个
        参数过来用以表示要调用哪个方法,注意这个int类型的标识必须介于 FIRST_CALL_TRANSACTION 和
        LAST_CALL_TRANSACTION之间，所以我们给方法分配code的时候最好使用FIRST_CALL_TRANSACTION+n 这种方式
        data ：Client传递过来的序列化数据包，Parcel类型
        reply： 如果Client端调用时需要返回值，Server通过这个对象将返回值传递回去，同样Parcel类型
        flag 用来区分这个调用是普通调用还是单边调用，普通调用时，Client端线程会阻塞，直到从Server端接收到返回值（所以如
        果Client端是主线程调用，其调用的Server端不宜做耗时操作，这会让造成Client的ANR），若flag==IBinder.FLAG_ONEWAY，
        则这次调用是单边调用，Client在传出数据后会立即执行下一段代码，此时两端异步执行，单边调用时函数返回值必须为void （也
        就是异步调用必须舍弃返回值，要返回值就必须阻塞等待）*/

        // 注：在发送数据后，Client进程的该线程会暂时被挂起
        // 所以，若Server进程执行的耗时操作，请不要使用主线程，以防止ANR
        // 3. Binder驱动根据 代理对象 找到对应的真身Binder对象所在的Server 进程（系统自动执行）
        // 4. Binder驱动把 数据 发送到Server 进程中，并通知Server 进程执行解包（系统自动执行）

        mHandler.postDelayed(task, 2000);
    }

    private ServiceConnection conn2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mIBinder = iBinder;
            compute2();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private void compute() {
        if (service1 == null) {
            return;
        }
        if (num >= 10) {
            unbindService(conn1);
            return;
        }
        int res = service1.add2(num, num++);
        L.e(TAG, "compute : " + res);
        T.show("compute : " + res);
        mHandler.postDelayed(task, 2000);
    }

    private Runnable task = new Runnable() {
        @Override
        public void run() {
//            compute();
            compute2();
        }
    };

    private ServiceConnection conn1 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            L.e(TAG, "onServiceConnected : " + componentName);
            BinderService.Stub stub = (BinderService.Stub) iBinder;
            service1 = stub.getService();
            compute();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            // client和Service解除绑定时，onServiceDisconnected并不会被调用；
            // onServiceDisconnected被调用的情况是发生在client和Service连接意外丢失时，
            // 这时client和Service一定是断开连接了
            L.e(TAG, "onServiceDisconnected : " + componentName);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            unbindService(conn2);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
