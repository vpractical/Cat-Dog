package com.y.mvp.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
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
import com.y.permissionlib.PermCat;
import com.y.permissionlib.PermissionCat;
import com.y.router_annotations.Route;
import com.y.util.ImageUtil;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity, view, "more_item").toBundle());
        }else{
            activity.startActivity(intent);
        }
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
    private static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;

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

    @PermCat(PERMISSION_CAMERA)
    public void chooseImage() {
        if (PermissionCat.has(this, permsNeed)) {
            ImageUtil.chooseImage(mActivity, CHOOSE_REQUEST_CODE);
        } else {
            PermissionCat.request("字符画需要这些权限", this, null, permsNeed);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
