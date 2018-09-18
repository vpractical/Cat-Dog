package com.y.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.chrisbanes.photoview.PhotoView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.y.R;
import com.y.mvp.base.BaseActivity;
import com.y.mvp.util.persenter.EmptyContract;
import com.y.mvp.util.persenter.EmptyPresenter;
import com.y.util.ImageUtil;
import com.y.util.T;

import java.util.List;

import butterknife.BindView;

public class ASCIIImageActivity extends BaseActivity<EmptyPresenter> implements EmptyContract.View {

    public static void start(Context context){
        Intent intent = new Intent(context,ASCIIImageActivity.class);
        context.startActivity(intent);
    }

    private static final String TAG = "ASCIIImageActivity";
    private static final int CHOOSE_REQUEST_CODE = 1001;
    private Bitmap asciiBitmap;
    @BindView(R.id.iv_ascii)
    PhotoView ivAscii;

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

    private void initToolbar(){
        mToolBar.inflateMenu(R.menu.ascii_menu);
        mToolBar.getTitleView().setText("字符绘制图片");
    }

    private void initListener(){
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_create:
                        ImageUtil.chooseImage(mActivity,CHOOSE_REQUEST_CODE);
                        break;
                    case R.id.action_save:
                        if(asciiBitmap != null){
                            ImageUtil.saveBitmap2Local(asciiBitmap,ImageUtil.IMAGE_DIR_LOCAL + System.currentTimeMillis() + "ascII.png",false);
                            T.show("已保存");
                        }else{
                            T.show("未获取ascII图片");
                        }
                        break;
                    case R.id.action_clean:

                        T.show("已清空");
                        break;

                }
                return false;
            }
        });
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
                
                if(path != null){
                    String filePath = ImageUtil.amendRotatePhoto(path);
                    asciiBitmap = ImageUtil.createAsciiPic(filePath);
                    ivAscii.setImageBitmap(asciiBitmap);
                }
            }
        }
    }

}
