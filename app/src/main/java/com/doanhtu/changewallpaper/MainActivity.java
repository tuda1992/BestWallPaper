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
    private int mRes = R.drawable.content_music;

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

        mMainFragment = MainFragment.newInstance(mRes);
        replaceFragment(mMainFragment, Constants.MAIN_FRAGMENT);

        setUpActionBar();
        createMenuList();
        mViewAnimator = new yalantis.com.sidemenu.util.ViewAnimator(this, mListData, mMainFragment, mDrawerLayout, this);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(MainFragment.CLOSE, R.drawable.icn_close);
        mListData.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(MainFragment.BUILDING, R.drawable.icn_1);
        mListData.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(MainFragment.BOOK, R.drawable.icn_2);
        mListData.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(MainFragment.PAINT, R.drawable.icn_3);
        mListData.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(MainFragment.CASE, R.drawable.icn_4);
        mListData.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(MainFragment.SHOP, R.drawable.icn_5);
        mListData.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(MainFragment.PARTY, R.drawable.icn_6);
        mListData.add(menuItem6);
        SlideMenuItem menuItem7 = new SlideMenuItem(MainFragment.MOVIE, R.drawable.icn_7);
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

    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition) {
        this.mRes = this.mRes == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;

        int finalRadius = Math.max(mLlContentFrame.getWidth(), mLlContentFrame.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(mLlContentFrame, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(yalantis.com.sidemenu.util.ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        mLlContentOverlay.setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        MainFragment mainFragment = MainFragment.newInstance(this.mRes);
        replaceFragment(mainFragment, Constants.MAIN_FRAGMENT);
        return mainFragment;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case MainFragment.CLOSE:
                return screenShotable;
            default:
                return replaceFragment(screenShotable, position);
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
