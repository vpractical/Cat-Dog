package com.y.mvp.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arcsoft.ageestimation.ASAE_FSDKAge;
import com.arcsoft.ageestimation.ASAE_FSDKEngine;
import com.arcsoft.ageestimation.ASAE_FSDKFace;
import com.arcsoft.ageestimation.ASAE_FSDKVersion;
import com.arcsoft.facerecognition.AFR_FSDKEngine;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.arcsoft.facerecognition.AFR_FSDKMatching;
import com.arcsoft.facerecognition.AFR_FSDKVersion;
import com.arcsoft.facetracking.AFT_FSDKEngine;
import com.arcsoft.facetracking.AFT_FSDKFace;
import com.arcsoft.facetracking.AFT_FSDKVersion;
import com.arcsoft.genderestimation.ASGE_FSDKEngine;
import com.arcsoft.genderestimation.ASGE_FSDKFace;
import com.arcsoft.genderestimation.ASGE_FSDKGender;
import com.arcsoft.genderestimation.ASGE_FSDKVersion;
import com.guo.android_extend.java.AbsLoop;
import com.guo.android_extend.java.ExtByteArrayOutputStream;
import com.guo.android_extend.tools.CameraHelper;
import com.guo.android_extend.widget.CameraFrameData;
import com.guo.android_extend.widget.CameraGLSurfaceView;
import com.guo.android_extend.widget.CameraSurfaceView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.y.R;
import com.y.util.ImageUtil;
import com.y.util.L;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ByFaceFragment extends BaseLoginFragment {

    public static ByFaceFragment newInstance() {
        ByFaceFragment byFace = new ByFaceFragment();
        return byFace;
    }

    private static final String TAG = "ByFaceFragment";
    private static final int CHOOSE_REQUEST_CODE = 1002;

    @BindView(R.id.surfaceView_login_face)
    CameraSurfaceView mSurfaceView;
    @BindView(R.id.glSurfaceView_login_face)
    CameraGLSurfaceView mGLSurfaceView;
    @BindView(R.id.tv_login_face_nick)
    TextView tvNick;
    @BindView(R.id.tv_login_face_sex_age)
    TextView tvSexAge;
    @BindView(R.id.iv_login_face_preview)
    ImageView mImageView;

    FaceDB mFaceDB;

    private int mWidth, mHeight, mFormat = ImageFormat.NV21;
    private Camera mCamera;

    AFT_FSDKVersion version = new AFT_FSDKVersion();
    AFT_FSDKEngine engine = new AFT_FSDKEngine();
    ASAE_FSDKVersion mAgeVersion = new ASAE_FSDKVersion();
    ASAE_FSDKEngine mAgeEngine = new ASAE_FSDKEngine();
    ASGE_FSDKVersion mGenderVersion = new ASGE_FSDKVersion();
    ASGE_FSDKEngine mGenderEngine = new ASGE_FSDKEngine();
    List<AFT_FSDKFace> result = new ArrayList<>();//扫描到的结果集
    List<ASAE_FSDKAge> ages = new ArrayList<>();
    List<ASGE_FSDKGender> genders = new ArrayList<>();

    int mCameraID = Camera.CameraInfo.CAMERA_FACING_FRONT;//Camera.CameraInfo.CAMERA_FACING_BACK;
    int mCameraRotate = 270;//90;
    boolean mCameraMirror = true;//false;
    byte[] mImageNV21 = null;
    FRAbsLoop mFRAbsLoop = null;
    AFT_FSDKFace mAFT_FSDKFace = null;
    Handler mHandler = new Handler();
    boolean isPostted = false;//未获取到结果集时，间隔刷新小图
    Runnable hide = new Runnable() {
        @Override
        public void run() {
            tvNick.setAlpha(0.5f);
            mImageView.setImageAlpha(128);
            isPostted = false;
        }
    };

    class FRAbsLoop extends AbsLoop {

        AFR_FSDKVersion version = new AFR_FSDKVersion();
        AFR_FSDKEngine engine = new AFR_FSDKEngine();
        AFR_FSDKFace result = new AFR_FSDKFace();
        List<FaceDB.FaceRegist> mResgist = mFaceDB.mRegister;
        List<ASAE_FSDKFace> face1 = new ArrayList<>();
        List<ASGE_FSDKFace> face2 = new ArrayList<>();

        @Override
        public void setup() {
            engine.AFR_FSDK_InitialEngine(FaceDB.appid, FaceDB.fr_key);
            engine.AFR_FSDK_GetVersion(version);
        }

        @Override
        public void loop() {
            if (mImageNV21 != null) {
                final int rotate = mCameraRotate;
                engine.AFR_FSDK_ExtractFRFeature(mImageNV21, mWidth, mHeight, AFR_FSDKEngine.CP_PAF_NV21, mAFT_FSDKFace.getRect(), mAFT_FSDKFace.getDegree(), result);
                AFR_FSDKMatching score = new AFR_FSDKMatching();
                float max = 0.0f;
                String name = null;
                for (FaceDB.FaceRegist fr : mResgist) {
                    for (AFR_FSDKFace face : fr.mFaceList) {
                        engine.AFR_FSDK_FacePairMatching(result, face, score);
                        if (max < score.getScore()) {
                            max = score.getScore();
                            name = fr.mName;
                        }
                    }
                }

                face1.clear();
                face2.clear();
                face1.add(new ASAE_FSDKFace(mAFT_FSDKFace.getRect(), mAFT_FSDKFace.getDegree()));
                face2.add(new ASGE_FSDKFace(mAFT_FSDKFace.getRect(), mAFT_FSDKFace.getDegree()));
                mAgeEngine.ASAE_FSDK_AgeEstimation_Image(mImageNV21, mWidth, mHeight, AFT_FSDKEngine.CP_PAF_NV21, face1, ages);
                mGenderEngine.ASGE_FSDK_GenderEstimation_Image(mImageNV21, mWidth, mHeight, AFT_FSDKEngine.CP_PAF_NV21, face2, genders);
                final String age = ages.get(0).getAge() == 0 ? "年龄未知" : ages.get(0).getAge() + "岁";
                final String gender = genders.get(0).getGender() == -1 ? "性别未知" : (genders.get(0).getGender() == 0 ? "男" : "女");

                //crop
                byte[] data = mImageNV21;
                YuvImage yuv = new YuvImage(data, ImageFormat.NV21, mWidth, mHeight, null);
                ExtByteArrayOutputStream ops = new ExtByteArrayOutputStream();
                yuv.compressToJpeg(mAFT_FSDKFace.getRect(), 80, ops);
                final Bitmap bmp = BitmapFactory.decodeByteArray(ops.getByteArray(), 0, ops.getByteArray().length);
                try {
                    ops.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (max > 0.6f) {
                    //fr success.
                    final float max_score = max;
                    L.e(TAG, "fit Score:" + max + ", NAME:" + name);
                    final String mNameShow = name;
                    mHandler.removeCallbacks(hide);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            tvNick.setAlpha(1.0f);
                            tvNick.setText(mNameShow);
                            tvNick.setTextColor(Color.RED);
                            tvSexAge.setVisibility(View.VISIBLE);
                            tvSexAge.setText("置信度：" + (float) ((int) (max_score * 1000)) / 1000.0);
                            tvSexAge.setTextColor(Color.RED);
                            mImageView.setRotation(rotate);
                            if (mCameraMirror) {
                                mImageView.setScaleY(-1);
                            }
                            mImageView.setImageAlpha(255);
                            mImageView.setImageBitmap(bmp);
                        }
                    });
                } else {
                    final String mNameShow = "未识别";
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvNick.setAlpha(1.0f);
                            tvSexAge.setVisibility(View.VISIBLE);
                            tvSexAge.setText(gender + "," + age);
                            tvSexAge.setTextColor(Color.RED);
                            tvNick.setText(mNameShow);
                            tvNick.setTextColor(Color.RED);
                            mImageView.setImageAlpha(255);
                            mImageView.setRotation(rotate);
                            if (mCameraMirror) {
                                mImageView.setScaleY(-1);
                            }
                            mImageView.setImageBitmap(bmp);
                        }
                    });
                }
                mImageNV21 = null;
            }

        }

        @Override
        public void over() {
            engine.AFR_FSDK_UninitialEngine();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_login_face;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void init() {
        mFaceDB = new FaceDB(mActivity.getExternalCacheDir().getPath());
        mSurfaceView.setupGLSurafceView(mGLSurfaceView, true, mCameraMirror, mCameraRotate);
        mSurfaceView.debug_print_fps(false, false);
        engine.AFT_FSDK_InitialFaceEngine(FaceDB.appid, FaceDB.ft_key, AFT_FSDKEngine.AFT_OPF_0_HIGHER_EXT, 16, 5);
        engine.AFT_FSDK_GetVersion(version);

        mAgeEngine.ASAE_FSDK_InitAgeEngine(FaceDB.appid, FaceDB.age_key);
        mAgeEngine.ASAE_FSDK_GetVersion(mAgeVersion);

        mGenderEngine.ASGE_FSDK_InitgGenderEngine(FaceDB.appid, FaceDB.gender_key);
        mGenderEngine.ASGE_FSDK_GetVersion(mGenderVersion);
        mFRAbsLoop = new FRAbsLoop();
        mFRAbsLoop.start();
        initListener();
    }

    private void initListener() {
        mSurfaceView.setOnCameraListener(new CameraSurfaceView.OnCameraListener() {
            @Override
            public Camera setupCamera() {
                mCamera = Camera.open(mCameraID);
                try {
                    Camera.Parameters parameters = mCamera.getParameters();
                    parameters.setPreviewSize(mWidth, mHeight);
                    parameters.setPreviewFormat(mFormat);

//                    for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
//                        L.e(TAG, "SIZE:" + size.width + "x" + size.height);
//                    }
//                    for (Integer format : parameters.getSupportedPreviewFormats()) {
//                        L.e(TAG, "FORMAT:" + format);
//                    }

//                    List<int[]> fps = parameters.getSupportedPreviewFpsRange();
//                    for (int[] count : fps) {
//                        L.e(TAG, "T:");
//                        for (int data : count) {
//                            L.e(TAG, "V=" + data);
//                        }
//                    }

                    //parameters.setPreviewFpsRange(15000, 30000);
                    //parameters.setExposureCompensation(parameters.getMaxExposureCompensation());
                    //parameters.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
                    //parameters.setAntibanding(Camera.Parameters.ANTIBANDING_AUTO);
                    //parmeters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                    //parameters.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
                    //parameters.setColorEffect(Camera.Parameters.EFFECT_NONE);
                    mCamera.setParameters(parameters);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mCamera != null) {
                    mWidth = mCamera.getParameters().getPreviewSize().width;
                    mHeight = mCamera.getParameters().getPreviewSize().height;
                }
                return mCamera;
            }

            @Override
            public void setupChanged(int format, int width, int height) {

            }

            @Override
            public boolean startPreviewImmediately() {
                return true;
            }

            @Override
            public Object onPreview(byte[] data, int width, int height, int format, long timestamp) {
                engine.AFT_FSDK_FaceFeatureDetect(data, width, height, AFT_FSDKEngine.CP_PAF_NV21, result);
                if (mImageNV21 == null) {
                    if (!result.isEmpty()) {
                        mAFT_FSDKFace = result.get(0).clone();
                        mImageNV21 = data.clone();
                    } else {
                        if (!isPostted) {
                            mHandler.removeCallbacks(hide);
                            mHandler.postDelayed(hide, 2000);
                            isPostted = true;
                        }
                    }
                }
                Rect[] rects = new Rect[result.size()];
                for (int i = 0; i < result.size(); i++) {
                    rects[i] = new Rect(result.get(i).getRect());
                }
                result.clear();
                return rects;
            }

            @Override
            public void onBeforeRender(CameraFrameData data) {

            }

            @Override
            public void onAfterRender(CameraFrameData data) {
                if (getUserVisibleHint()) {
                    mGLSurfaceView.getGLES2Render().draw_rect((Rect[]) data.getParams(), Color.GREEN, 2);
                }
            }
        });

        mGLSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CameraHelper.touchFocus(mCamera, event, v, new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        if (success) {
                            L.e(TAG, "Camera Focus SUCCESS!");
                        }
                    }
                });
                return false;
            }
        });
    }

    private void switchCemare() {
        if (mCameraID == Camera.CameraInfo.CAMERA_FACING_BACK) {
            mCameraID = Camera.CameraInfo.CAMERA_FACING_FRONT;
            mCameraRotate = 270;
            mCameraMirror = true;
        } else {
            mCameraID = Camera.CameraInfo.CAMERA_FACING_BACK;
            mCameraRotate = 90;
            mCameraMirror = false;
        }
        mSurfaceView.resetCamera();
        mGLSurfaceView.setRenderConfig(mCameraRotate, mCameraMirror);
        mGLSurfaceView.getGLES2Render().setViewAngle(mCameraMirror, mCameraRotate);
    }

    private void register() {
        ImageUtil.chooseImage(this, CHOOSE_REQUEST_CODE);
    }

    @Override
    public void onStart() {
        super.onStart();
        ((LoginActivity) mActivity).getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_register:
                        register();
                        break;
                    case R.id.action_camera_switch:
                        switchCemare();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFRAbsLoop.shutdown();
        engine.AFT_FSDK_UninitialFaceEngine();
        mAgeEngine.ASAE_FSDK_UninitAgeEngine();
        mGenderEngine.ASGE_FSDK_UninitGenderEngine();
        mHandler.removeCallbacks(hide);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_REQUEST_CODE) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            String path = null;
            if (selectList != null && selectList.size() > 0) {
                LocalMedia localMedia = selectList.get(0);
                if (localMedia.isCompressed()) {
                    path = localMedia.getCompressPath();
                } else if (localMedia.isCut()) {
                    path = localMedia.getCutPath();
                } else {
                    path = localMedia.getPath();
                }
            }

            if (path != null) {
                String filePath = ImageUtil.amendRotatePhoto(path);
                RegisterFaceActivity.start(mActivity, filePath);
            }
        }
    }
}
