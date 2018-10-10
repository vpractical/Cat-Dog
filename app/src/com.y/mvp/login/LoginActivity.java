package com.y.mvp.login;


import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.umeng.socialize.UMShareAPI;
import com.y.R;
import com.y.config.SystemConfig;
import com.y.config.Key;
import com.y.mvp.base.BaseActivity;
import com.y.mvp.util.persenter.EmptyContract;
import com.y.mvp.util.persenter.EmptyPresenter;
import com.y.mvp.view.AppToolbar;
import com.y.util.PermissionUtil;
import com.y.util.SPUtil;

import java.util.List;

public class LoginActivity extends BaseActivity<EmptyPresenter> implements EmptyContract.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    private List<Pair<Integer, String>> loginTypes = SystemConfig.loginTypes();
    private int loginTypeIndex = -1;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void init() {
        initToolbar();
        initLoginType();
        initListener();
    }

    private void initLoginType() {
        showLogin(SPUtil.getCommonInt(Key.LOGIN_TYPE, 0));
        showLoginSwitch();
    }

    private void initToolbar() {
        mToolBar.inflateMenu(R.menu.login_menu);
        mToolBar.getIconView().setVisibility(View.GONE);
        mToolBar.getTitleView().setText("登录");
    }

    private void initListener() {

    }

    public AppToolbar getToolbar() {
        return mToolBar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void showLogin(int index) {
        if (loginTypeIndex == index) {
            return;
        }
        mToolBar.getMenu().findItem(R.id.action_camera_switch).setVisible(false);
        mToolBar.getMenu().findItem(R.id.action_register).setVisible(false);

        BaseLoginFragment fragment;

        switch (index) {
            case SystemConfig.LOGIN_BY_CODE:
                fragment = ByCodeFragment.newInstance();
                break;
            case SystemConfig.LOGIN_BY_PWD:
                fragment = ByPwdFragment.newInstance();
                break;
            case SystemConfig.LOGIN_BY_FACE:
                if (!permissionCheck()) {
                    return;
                }
                fragment = ByFaceFragment.newInstance();
                mToolBar.getMenu().findItem(R.id.action_camera_switch).setVisible(true);
                mToolBar.getMenu().findItem(R.id.action_register).setVisible(true);
                break;
            case SystemConfig.LOGIN_BY_THIRD:
                fragment = ByThirdFragment.newInstance();
                break;
            case SystemConfig.LOGIN_BY_VISIT:
            default:
                fragment = ByVisitFragment.newInstance();
                break;
        }
        loadRootFragment(R.id.container_login, fragment, false, false);
        mToolBar.getTitleView().setText(loginTypes.get(index).second);
        loginTypeIndex = index;
        SPUtil.putCommonInt(Key.LOGIN_TYPE, loginTypeIndex);
    }

    public boolean permissionCheck() {
        String perm = Manifest.permission.CAMERA;
        if (!PermissionUtil.getInstance().has(mActivity, perm)) {
            PermissionUtil.getInstance().callback(new PermissionUtil.PermissionCallback() {
                @Override
                public void onGranted(List<String> perms) {
                    if (permissionCheck()) {
                        showLogin(SystemConfig.LOGIN_BY_FACE);
                    }
                }

                @Override
                public void onDenied(List<String> perms) {
                    PermissionUtil.getInstance().neverAsk(mActivity, "权限被禁止，请设置", perms);
                }

                @Override
                public void onSettingGranted() {
                    if (permissionCheck()) {
                        showLogin(SystemConfig.LOGIN_BY_FACE);
                    }
                }
            }).request(mActivity, "刷脸需相机权限，请允许", perm);
            return false;
        }
        return true;
    }


    /**
     * 切换登录方式
     */
    private void showLoginSwitch() {
        final ImageView fabIconNew = new ImageView(this);
        // 设置菜单按钮Button的图标
        fabIconNew.setImageResource(R.drawable.setting_dark);
        final FloatingActionButton rightLowerButton = new FloatingActionButton.Builder(this)
                .setPosition(FloatingActionButton.POSITION_BOTTOM_RIGHT).setContentView(fabIconNew).build();
        rightLowerButton.setBackgroundColor(Color.TRANSPARENT);

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);
        ImageView rlIcon1 = new ImageView(this);
        ImageView rlIcon2 = new ImageView(this);
        ImageView rlIcon3 = new ImageView(this);
        ImageView rlIcon4 = new ImageView(this);
        ImageView rlIcon5 = new ImageView(this);
        // 设置弹出菜单的图标
        rlIcon1.setImageResource(R.drawable.login_by_code);
        rlIcon2.setImageResource(R.drawable.login_by_pwd);
        rlIcon3.setImageResource(R.drawable.scan_dark);
        rlIcon4.setImageResource(R.drawable.more_white);
        rlIcon5.setImageResource(R.drawable.login_by_visit);

        SubActionButton sab1 = rLSubBuilder.setContentView(rlIcon1).build();
        SubActionButton sab2 = rLSubBuilder.setContentView(rlIcon2).build();
        SubActionButton sab3 = rLSubBuilder.setContentView(rlIcon3).build();
        SubActionButton sab4 = rLSubBuilder.setContentView(rlIcon4).build();
        SubActionButton sab5 = rLSubBuilder.setContentView(rlIcon5).build();

        //子菜单和按钮距离，子菜单的显示角度
        int actionMenuRadius = getResources().getDimensionPixelSize(R.dimen.dp108);
        final FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(sab1)
                .addSubActionView(sab2)
                .addSubActionView(sab3)
                .addSubActionView(sab4)
                .addSubActionView(sab5)
                .attachTo(rightLowerButton)
                .setRadius(actionMenuRadius)
                .setStartAngle(175)
                .setEndAngle(275)
                .build();

        final Runnable closeTask = new Runnable() {
            @Override
            public void run() {
                if (rightLowerMenu != null && rightLowerMenu.isOpen()) {
                    rightLowerMenu.close(true);
                }
            }
        };

        rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {

            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // 逆时针旋转90°
                fabIconNew.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, -90);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                animation.start();

                fabIconNew.removeCallbacks(closeTask);
                fabIconNew.postDelayed(closeTask, 3000);
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // 顺时针旋转90°
                fabIconNew.setRotation(-90);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                animation.start();
            }
        });

        sab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightLowerMenu.close(true);
                showLogin(SystemConfig.LOGIN_BY_CODE);
            }
        });
        sab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightLowerMenu.close(true);
                showLogin(SystemConfig.LOGIN_BY_PWD);
            }
        });
        sab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightLowerMenu.close(true);
                showLogin(SystemConfig.LOGIN_BY_FACE);
            }
        });
        sab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightLowerMenu.close(true);
                showLogin(SystemConfig.LOGIN_BY_THIRD);
            }
        });
        sab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightLowerMenu.close(true);
                showLogin(SystemConfig.LOGIN_BY_VISIT);
            }
        });
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
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
