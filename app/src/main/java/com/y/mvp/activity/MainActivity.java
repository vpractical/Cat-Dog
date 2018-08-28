package com.y.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.y.R;
import com.y.adapter.MainPagerAdapter;
import com.y.bean.User;
import com.y.config.Key;
import com.y.imageloader.ImageLoader;
import com.y.listener.OnDrawerListener;
import com.y.mvp.activity.presenter.MainPresenter;
import com.y.mvp.base.BaseActivity;
import com.y.mvp.base.BaseFragment;
import com.y.mvp.fragment.ChatFragment;
import com.y.mvp.fragment.GameFragment;
import com.y.mvp.fragment.MoreFragment;
import com.y.mvp.fragment.NewsFragment;
import com.y.mvp.fragment.VideoFragment;
import com.y.util.SPUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> {

    public static void start(Context context){
        Intent intent = new Intent(context,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.vp_main_container)
    ViewPager vpContainer;
    @BindView(R.id.navbar_main)
    BottomNavigationBar navBar;
    @BindView(R.id.drawer_main)
    DrawerLayout drawerRoot;

    private List<BaseFragment> fragmentList = new ArrayList<>();
    private int currentIndex;

    /**
     * 控制双击退出
     */
    private boolean clickToExit;

    private User mUser = (User) SPUtil.getSingleObject(Key.USER_KEY,User.class);

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void init() {
        drawerRoot.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.END);
        mUser = (User) SPUtil.getSingleObject(Key.USER_KEY,User.class);
        initFragment();
        initToolbar();
        initNavBar();
        initListener();
    }

    private void initFragment() {
        fragmentList.clear();
        fragmentList.add(NewsFragment.newInstance());
        fragmentList.add(ChatFragment.newInstance());
        fragmentList.add(GameFragment.newInstance());
        fragmentList.add(VideoFragment.newInstance());
        fragmentList.add(MoreFragment.newInstance());
        vpContainer.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), fragmentList));
        vpContainer.setCurrentItem(currentIndex);
    }

    private void initToolbar() {
        setSupportActionBar(mToolBar);
        setTitle("");
        mToolBar.inflateMenu(R.menu.main_menu);
        ImageView ivIcon = mToolBar.getIconView();
        ivIcon.setPadding(0, 0, 0, 0);
        ImageLoader.with(mActivity).url(mUser.avatar).circle().into(ivIcon);
        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawerRoot.isDrawerOpen(Gravity.START)) {
                    drawerRoot.openDrawer(Gravity.START);
                }
            }
        });
        mToolBar.getTitleView().setText("首页");
    }

    private void initNavBar() {
        BadgeItem badgeItem = new BadgeItem().setBackgroundColor(Color.RED).setText("1");
        navBar.setMode(BottomNavigationBar.MODE_FIXED);
        navBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        navBar.setBarBackgroundColor(R.color.white);
        navBar
                .addItem(new BottomNavigationItem(R.drawable.select_main_news_nav, "新闻").setActiveColorResource(R.color.colorPrimaryDark))
                .addItem(new BottomNavigationItem(R.drawable.select_main_chat_nav, "聊天").setActiveColorResource(R.color.colorPrimaryDark).setBadgeItem(badgeItem))
                .addItem(new BottomNavigationItem(R.drawable.select_main_game_nav, "游戏").setActiveColorResource(R.color.colorPrimaryDark))
                .addItem(new BottomNavigationItem(R.drawable.select_main_video_nav, "视频").setActiveColorResource(R.color.colorPrimaryDark))
                .addItem(new BottomNavigationItem(R.drawable.select_main_more_nav, "便利").setActiveColorResource(R.color.colorPrimaryDark))
                .setFirstSelectedPosition(0)
                .initialise(); //所有的设置需在调用该方法前完成
    }

    private void initListener() {
        vpContainer.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position != currentIndex) {
                    currentIndex = position;
                    navBar.selectTab(position);
                }
            }
        });

        navBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                //未选中 -> 选中
                if (position != currentIndex) {
                    currentIndex = position;
                    vpContainer.setCurrentItem(position);
                }
            }

            @Override
            public void onTabUnselected(int position) {
                //选中 -> 未选中

            }

            @Override
            public void onTabReselected(int position) {
                //选中 -> 选中

            }
        });

        drawerRoot.addDrawerListener(new OnDrawerListener(drawerRoot));

        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_scan:

                        break;
                    case R.id.action_qrcode:

                        break;
                    case R.id.action_settings:
                        if (!drawerRoot.isDrawerOpen(Gravity.END)) {
                            drawerRoot.openDrawer(Gravity.END);
                        }
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        try {
            Field field = menu.getClass().getDeclaredField("mOptionalIconsVisible");
            field.setAccessible(true);
            field.set(menu, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(!clickToExit){
                clickToExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clickToExit = false;
                    }
                },300);
            }else{
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
