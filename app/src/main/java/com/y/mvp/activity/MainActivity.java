package com.y.mvp.activity;

        import android.graphics.Color;
        import android.support.v4.view.ViewPager;
        import android.support.v4.widget.DrawerLayout;
        import android.view.Gravity;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;

        import com.ashokvarma.bottomnavigation.BadgeItem;
        import com.ashokvarma.bottomnavigation.BottomNavigationBar;
        import com.ashokvarma.bottomnavigation.BottomNavigationItem;
        import com.y.R;
        import com.y.adapter.MainPagerAdapter;
        import com.y.listener.OnDrawerListener;
        import com.y.mvp.activity.presenter.MainPresenter;
        import com.y.mvp.base.BaseActivity;
        import com.y.mvp.base.BaseFragment;
        import com.y.mvp.fragment.ChatFragment;
        import com.y.mvp.fragment.GameFragment;
        import com.y.mvp.fragment.ToolFragment;

        import java.util.ArrayList;
        import java.util.List;

        import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> {

    @BindView(R.id.vp_main_container)
    ViewPager vpContainer;
    @BindView(R.id.navbar_main)
    BottomNavigationBar navBar;
    @BindView(R.id.drawer_main)
    DrawerLayout drawerRoot;

    private List<BaseFragment> fragmentList = new ArrayList<>();
    private MainPagerAdapter fragmentAdapter;
    private int currentIndex;

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

        initFragment();
        initToolbar();
        initNavBar();
        initListener();
    }

    private void initFragment() {
        fragmentList.clear();
        fragmentList.add(ChatFragment.newInstance());
        fragmentList.add(GameFragment.newInstance());
        fragmentList.add(ToolFragment.newInstance());
        vpContainer.setAdapter(fragmentAdapter = new MainPagerAdapter(getSupportFragmentManager(), fragmentList));
        vpContainer.setCurrentItem(currentIndex);
    }

    private void initToolbar() {
        setTitle("");
        mToolBar.inflateMenu(R.menu.main_menu);
        mIvBack.setPadding(0, 0, 0, 0);
        mIvBack.setImageResource(R.mipmap.ic_launcher);
    }

    private void initNavBar() {
        BadgeItem badgeItem = new BadgeItem().setBackgroundColor(Color.RED).setText("1");
        navBar.setMode(BottomNavigationBar.MODE_FIXED);
        navBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        navBar.setBarBackgroundColor(R.color.white);
        navBar.addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "聊天").setActiveColorResource(R.color.colorPrimaryDark).setBadgeItem(badgeItem))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "游戏").setActiveColorResource(R.color.colorPrimaryDark))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "便利").setActiveColorResource(R.color.colorPrimaryDark))
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

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawerRoot.isDrawerOpen(Gravity.START)) {
                    drawerRoot.openDrawer(Gravity.START);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_scan:

                break;
            case R.id.action_qrcode:

                break;
            case R.id.action_settings:
                if (!drawerRoot.isDrawerOpen(Gravity.START)) {
                    drawerRoot.openDrawer(Gravity.START);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
