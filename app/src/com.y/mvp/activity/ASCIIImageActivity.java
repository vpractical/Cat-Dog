package com.y.mvp.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.github.chrisbanes.photoview.PhotoView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.y.R;
import com.y.mvp.base.BaseActivity;
import com.y.mvp.util.persenter.EmptyContract;
import com.y.mvp.util.persenter.EmptyPresenter;
import com.y.mvp.view.ShareDialog;
import com.y.route.Route;
import com.y.util.ImageUtil;
import com.y.util.L;
import com.y.util.PermissionUtil;
import com.y.util.T;
import com.y.util.UMShareUtil;

import java.util.List;

import butterknife.BindView;

@Route(path = "app/ascii")
public class ASCIIImageActivity extends BaseActivity<EmptyPresenter> implements EmptyContract.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, ASCIIImageActivity.class);
        context.startActivity(intent);
    }

    /**
     * 元素过渡启动页面
     * 将两个Activity中需要过渡的View加上相同的android:transitionName属性
     */
    public static void start(Activity activity, View view) {
        Intent intent = new Intent(activity, ASCIIImageActivity.class);
        activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity, view, "more_item").toBundle());
    }


    private static final String TAG = "ASCIIImageActivity";
    private static final int CHOOSE_REQUEST_CODE = 1001;
    private Bitmap asciiBitmap;
    @BindView(R.id.iv_ascii)
    PhotoView ivAscii;

    /**
     * 选择照片需要的权限
     */
    private String[] permsNeed = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected int getLayout() {
        return R.layout.activity_ascii_image;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void init() {
        ivAscii.setImageResource(R.drawable.more_ascii_img);
        initToolbar();
        initListener();
    }

    private void initToolbar() {
        mToolBar.inflateMenu(R.menu.ascii_menu);
        mToolBar.getTitleView().setText("字符绘制图片");
    }

    private void initListener() {
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_create:
                        chooseImage();
                        break;
                    case R.id.action_save:
                        if (asciiBitmap != null) {
                            ImageUtil.saveBitmap2Local(asciiBitmap, ImageUtil.IMAGE_DIR_LOCAL + System.currentTimeMillis() + "ascII.png", false);
                            T.show("已保存");
                        } else {
                            T.show("未获取ascII图片");
                        }
                        break;
                    case R.id.action_clean:
                        T.show("已清空");
                        break;
                    case R.id.action_share:
                        UMShareUtil umShare = new UMShareUtil(mActivity).title("黄昏")
                                .text("字符图").url("https://www.baidu.com");
                        if (asciiBitmap == null) {
                            umShare.image(R.drawable.more_ascii_img);
                        } else {
                            umShare.image(asciiBitmap);
                        }

                        new ShareDialog(mActivity).action(umShare).show();
                        break;
                }
                return false;
            }
        });
    }

    public void chooseImage() {
        if (!PermissionUtil.getInstance().has(mActivity, permsNeed)) {
            PermissionUtil.getInstance().callback(new PermissionUtil.PermissionCallback() {
                @Override
                public void onGranted(List<String> perms) {
                    L.e("onPermissionsGranted : " + " size : " + perms.size());
                    if (perms.size() == permsNeed.length) {
                        chooseImage();
                    }
                }

                @Override
                public void onDenied(List<String> perms) {
                    L.e("onPermissionsDenied : " + " size : " + perms.size());
                    PermissionUtil.getInstance().neverAsk(mActivity, "权限被禁止，请允许", perms);
                }

                @Override
                public void onSettingGranted() {
                    L.e("从设置界面回来");
                    if (PermissionUtil.getInstance().has(mActivity, permsNeed)) {
                        chooseImage();
                    }
                }
            }).request(mActivity, "字符画需要相机和存储权限，请允许", permsNeed);
        } else {
            L.e("有权限，进入页面");
            ImageUtil.chooseImage(mActivity, CHOOSE_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PermissionUtil.getInstance().onActivityResult(requestCode);
        if (resultCode == RESULT_OK) {
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
                    asciiBitmap = ImageUtil.createAsciiPic(filePath);
                    ivAscii.setImageBitmap(asciiBitmap);
                }
            }
        }

    }

}
