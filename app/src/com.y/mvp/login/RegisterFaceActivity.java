package com.y.mvp.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.arcsoft.facedetection.AFD_FSDKEngine;
import com.arcsoft.facedetection.AFD_FSDKError;
import com.arcsoft.facedetection.AFD_FSDKFace;
import com.arcsoft.facedetection.AFD_FSDKVersion;
import com.arcsoft.facerecognition.AFR_FSDKEngine;
import com.arcsoft.facerecognition.AFR_FSDKError;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.arcsoft.facerecognition.AFR_FSDKVersion;
import com.guo.android_extend.image.ImageConverter;
import com.guo.android_extend.widget.ExtImageView;
import com.guo.android_extend.widget.HListView;
import com.y.R;
import com.y.mvp.base.BaseActivity;
import com.y.util.ImageUtil;
import com.y.util.T;

import java.util.ArrayList;
import java.util.List;


public class RegisterFaceActivity extends BaseActivity {

    public static void start(Context context, String filePath) {
        Intent it = new Intent(context, RegisterFaceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("imagePath", filePath);
        it.putExtras(bundle);
        context.startActivity(it);
    }

    private final String TAG = "RegisterFaceActivity";
    private String mFilePath;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Bitmap mBitmap;
    private Rect src = new Rect();
    private Rect dst = new Rect();
    private Thread view;
    private EditText mEditText;
    private ExtImageView mExtImageView;
    private HListView mHListView;
    private RegisterViewAdapter mRegisterViewAdapter;
    private AFR_FSDKFace mAFR_FSDKFace;

    FaceDB mFaceDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("","11111111111111111111");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register_face;
    }

    @Override
    protected void initInject() {
    }

    @Override
    protected void init() {
        initView();
        initListener();
    }

    private void initView() {
        mFilePath = getIntent().getExtras().getString("imagePath");
        if (TextUtils.isEmpty(mFilePath)) {
            finish();
            return;
        }
        mFaceDB = new FaceDB(mActivity.getExternalCacheDir().getPath());
        mRegisterViewAdapter = new RegisterViewAdapter(this);
        mHListView = findViewById(R.id.hlistView);
        mHListView.setAdapter(mRegisterViewAdapter);
        mHListView.setOnItemClickListener(mRegisterViewAdapter);

        mBitmap = ImageUtil.decodeImage(mFilePath);
        src.set(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        mSurfaceView = findViewById(R.id.surfaceView);
        view = new Thread(new Runnable() {
            @Override
            public void run() {
                while (mSurfaceHolder == null) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                byte[] data = new byte[mBitmap.getWidth() * mBitmap.getHeight() * 3 / 2];

                try {
                    ImageConverter convert = new ImageConverter();
                    convert.initial(mBitmap.getWidth(), mBitmap.getHeight(), ImageConverter.CP_PAF_NV21);
                    if (convert.convert(mBitmap, data)) {
                        Log.d(TAG, "convert ok!");
                    }
                    convert.destroy();
                } catch (Exception e) {
                    e.printStackTrace();
                    T.show("图像格式错误 " + e.getMessage());
                }

                AFD_FSDKEngine engine = new AFD_FSDKEngine();
                AFD_FSDKVersion version = new AFD_FSDKVersion();
                List<AFD_FSDKFace> result = new ArrayList<AFD_FSDKFace>();
                AFD_FSDKError err = engine.AFD_FSDK_InitialFaceEngine(FaceDB.appid, FaceDB.fd_key, AFD_FSDKEngine.AFD_OPF_0_HIGHER_EXT, 16, 5);
                if (err.getCode() != AFD_FSDKError.MOK) {
                    T.show("FD初始化失败，错误码：" + err.getCode());
                }
                err = engine.AFD_FSDK_GetVersion(version);
                err = engine.AFD_FSDK_StillImageFaceDetection(data, mBitmap.getWidth(), mBitmap.getHeight(), AFD_FSDKEngine.CP_PAF_NV21, result);
                while (mSurfaceHolder != null) {
                    Canvas canvas = mSurfaceHolder.lockCanvas();
                    if (canvas != null) {
                        Paint mPaint = new Paint();
                        boolean fit_horizontal = canvas.getWidth() / (float) src.width() < canvas.getHeight() / (float) src.height() ? true : false;
                        float scale;
                        if (fit_horizontal) {
                            scale = canvas.getWidth() / (float) src.width();
                            dst.left = 0;
                            dst.top = (canvas.getHeight() - (int) (src.height() * scale)) / 2;
                            dst.right = dst.left + canvas.getWidth();
                            dst.bottom = dst.top + (int) (src.height() * scale);
                        } else {
                            scale = canvas.getHeight() / (float) src.height();
                            dst.left = (canvas.getWidth() - (int) (src.width() * scale)) / 2;
                            dst.top = 0;
                            dst.right = dst.left + (int) (src.width() * scale);
                            dst.bottom = dst.top + canvas.getHeight();
                        }
                        canvas.drawBitmap(mBitmap, src, dst, mPaint);
                        canvas.save();
                        canvas.scale((float) dst.width() / (float) src.width(), (float) dst.height() / (float) src.height());
                        canvas.translate(dst.left / scale, dst.top / scale);
                        for (AFD_FSDKFace face : result) {
                            mPaint.setColor(Color.GREEN);
                            mPaint.setStrokeWidth(3.0f);
                            mPaint.setStyle(Paint.Style.STROKE);
                            canvas.drawRect(face.getRect(), mPaint);
                        }
                        canvas.restore();
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                        break;
                    }
                }

                if (!result.isEmpty()) {
                    AFR_FSDKVersion version1 = new AFR_FSDKVersion();
                    AFR_FSDKEngine engine1 = new AFR_FSDKEngine();
                    AFR_FSDKFace result1 = new AFR_FSDKFace();
                    AFR_FSDKError error1 = engine1.AFR_FSDK_InitialEngine(FaceDB.appid, FaceDB.fr_key);
                    if (error1.getCode() != AFD_FSDKError.MOK) {
                        T.show("FR初始化失败，错误码：" + error1.getCode());
                    }
                    error1 = engine1.AFR_FSDK_GetVersion(version1);
                    error1 = engine1.AFR_FSDK_ExtractFRFeature(data, mBitmap.getWidth(), mBitmap.getHeight(), AFR_FSDKEngine.CP_PAF_NV21, new Rect(result.get(0).getRect()), result.get(0).getDegree(), result1);
                    if (error1.getCode() == error1.MOK) {
                        mAFR_FSDKFace = result1.clone();
                        int width = result.get(0).getRect().width();
                        int height = result.get(0).getRect().height();
                        final Bitmap face_bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                        Canvas face_canvas = new Canvas(face_bitmap);
                        face_canvas.drawBitmap(mBitmap, result.get(0).getRect(), new Rect(0, 0, width, height), null);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LayoutInflater inflater = LayoutInflater.from(mActivity);
                                View layout = inflater.inflate(R.layout.dialog_register, null);
                                mEditText = (EditText) layout.findViewById(R.id.editview);
                                mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
                                mExtImageView = (ExtImageView) layout.findViewById(R.id.extimageview);
                                mExtImageView.setImageBitmap(face_bitmap);
                                new AlertDialog.Builder(mActivity)
                                        .setTitle("请输入注册名字")
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .setView(layout)
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                mFaceDB.addFace(mEditText.getText().toString(), mAFR_FSDKFace);
                                                mRegisterViewAdapter.notifyDataSetChanged();
                                                dialog.dismiss();
                                            }
                                        })
                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .show();
                            }
                        });

                    } else {
                        T.show("人脸特征无法检测，请换一张图片");
                    }
                    error1 = engine1.AFR_FSDK_UninitialEngine();
                } else {
                    T.show("没有检测到人脸，请换一张图片");
                }
                err = engine.AFD_FSDK_UninitialFaceEngine();
            }
        });
        view.start();

    }

    private void initListener() {
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mSurfaceHolder = holder;
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mSurfaceHolder = null;
                try {
                    view.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class Holder {
        ExtImageView siv;
        TextView tv;
    }

    class RegisterViewAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
        Context mContext;
        LayoutInflater mLInflater;

        public RegisterViewAdapter(Context c) {
            mContext = c;
            mLInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return mFaceDB.mRegister.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView != null) {
                holder = (Holder) convertView.getTag();
            } else {
                convertView = mLInflater.inflate(R.layout.item_sample, null);
                holder = new Holder();
                holder.siv = convertView.findViewById(R.id.imageView1);
                holder.tv = convertView.findViewById(R.id.textView1);
                convertView.setTag(holder);
            }

            if (!mFaceDB.mRegister.isEmpty()) {
                FaceDB.FaceRegist face = mFaceDB.mRegister.get(position);
                holder.tv.setText(face.mName);
                convertView.setWillNotDraw(false);
            }

            return convertView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final String name = mFaceDB.mRegister.get(position).mName;
            final int count = mFaceDB.mRegister.get(position).mFaceList.size();
            new AlertDialog.Builder(mActivity)
                    .setTitle("删除注册名:" + name)
                    .setMessage("包含:" + count + "个注册人脸特征信息")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mFaceDB.delete(name);
                            mRegisterViewAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }
}
