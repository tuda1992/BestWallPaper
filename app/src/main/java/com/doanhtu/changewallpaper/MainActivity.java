package com.doanhtu.changewallpaper;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ViewAnimator;

import com.doanhtu.changewallpaper.base.BaseActivity;
import com.doanhtu.changewallpaper.constant.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;

public class MainActivity extends BaseActivity implements yalantis.com.sidemenu.util.ViewAnimator.ViewAnimatorListener {

    private ActionBarDrawerToggle mDrawerToggle;
    private List<SlideMenuItem> mListData = new ArrayList<>();
    private MainFragment mMainFragment;
    private yalantis.com.sidemenu.util.ViewAnimator mViewAnimator;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.ll_left_drawer)
    LinearLayout mLlLeftDrawer;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.content_overlay)
    LinearLayout mLlContentOverlay;
    @BindView(R.id.content_frame)
    LinearLayout mLlContentFrame;

    @OnClick(R.id.ll_left_drawer)
    public void onClickLeftDrawer() {
        mDrawerLayout.closeDrawers();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        setUpActionBar();
        goToFragment(1);
        createMenuList();
        mViewAnimator = new yalantis.com.sidemenu.util.ViewAnimator(this, mListData, mMainFragment, mDrawerLayout, this);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(MainFragment.CLOSE, R.drawable.icn_close);
        mListData.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(MainFragment.CAR, R.drawable.ic_car);
        mListData.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(MainFragment.MOTOR_BIKE, R.drawable.ic_motor_bike);
        mListData.add(menuItem2);
        SlideMenuItem menuItem8 = new SlideMenuItem(MainFragment.FOOT_BALL, R.drawable.ic_ball);
        mListData.add(menuItem8);
        SlideMenuItem menuItem3 = new SlideMenuItem(MainFragment.FLOWER, R.drawable.ic_flower);
        mListData.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(MainFragment.COMICS, R.drawable.ic_comics);
        mListData.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(MainFragment.WEATHER, R.drawable.ic_weather);
        mListData.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(MainFragment.DOTA, R.drawable.ic_dota);
        mListData.add(menuItem6);
        SlideMenuItem menuItem7 = new SlideMenuItem(MainFragment.LOL, R.drawable.ic_lol);
        mListData.add(menuItem7);
    }

    private void setUpActionBar() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                mToolBar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                mLlLeftDrawer.removeAllViews();
                mLlLeftDrawer.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && mLlLeftDrawer.getChildCount() == 0)
                    mViewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    protected void initListeners() {

    }

    @Override
    protected void initDatas(Bundle saveInstanceState) {

    }

    @Override
    protected void getData() {

    }

    private void goToFragment(int position) {
        mMainFragment = MainFragment.newInstance();
        replaceFragment(mMainFragment, Constants.MAIN_FRAGMENT);
        String title = "";

        switch (position) {
            case 1:
                title = "Car WallPaper";
                break;
            case 2:
                title = "MotorBike WallPaper";
                break;
            case 3:
                title = "Ball WallPaper";
                break;
            case 4:
                title = "Flower WallPaper";
                break;
            case 5:
                title = "Comics WallPaper";
                break;
            case 6:
                title = "Weather WallPaper";
                break;
            case 7:
                title = "Dota2 WallPaper";
                break;
            case 8:
                title = "LOL WallPaper";
                break;
        }
        getSupportActionBar().setTitle(title);

    }

    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition, int position) {
        int finalRadius = Math.max(mLlContentFrame.getWidth(), mLlContentFrame.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(mLlContentFrame, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(yalantis.com.sidemenu.util.ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        mLlContentOverlay.setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();

        goToFragment(position);

        return mMainFragment;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position, int index) {
        switch (slideMenuItem.getName()) {
            case MainFragment.CLOSE:
                return screenShotable;
            default:
                return replaceFragment(screenShotable, position, index);
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void addViewToContainer(View view) {
        mLlLeftDrawer.addView(view);
    }
}
