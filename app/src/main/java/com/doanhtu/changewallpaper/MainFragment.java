package com.doanhtu.changewallpaper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.doanhtu.changewallpaper.adapter.WallPaperAdapter;
import com.doanhtu.changewallpaper.base.BaseFragment;
import com.doanhtu.changewallpaper.constant.Constants;
import com.doanhtu.changewallpaper.model.WallPaperModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by doanhtu on 12/28/17.
 */

public class MainFragment extends BaseFragment implements ScreenShotable, WallPaperAdapter.ICallBackItemClick {

    public static final String CLOSE = "Close";
    public static final String CAR = "Car";
    public static final String MOTOR_BIKE = "Motorbike";
    public static final String SPORT = "Sport";
    public static final String FLOWER = "Flower";
    public static final String COMICS = "Comics";
    public static final String WEATHER = "Weather";
    public static final String DOTA = "Dota";
    public static final String LOL = "Lol";

    private Bitmap mBitMap;
    private WallPaperAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<WallPaperModel> mListData = new ArrayList<>();
    private int mCurrentFragment;

    @BindView(R.id.container_frame)
    View mContainerView;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.iv_background)
    ImageView mIvBackground;

    @BindDrawable(R.drawable.ic_logo_car)
    Drawable mLogoCar;
    @BindDrawable(R.drawable.ic_logo_flower)
    Drawable mLogoFlower;
    @BindDrawable(R.drawable.ic_logo_sport)
    Drawable mLogoSport;
    @BindDrawable(R.drawable.ic_logo_comics)
    Drawable mLogoComics;
    @BindDrawable(R.drawable.ic_logo_weather)
    Drawable mLogoWeather;
    @BindDrawable(R.drawable.ic_logo_dota2)
    Drawable mLogoDota;
    @BindDrawable(R.drawable.ic_logo_lol)
    Drawable mLogoLol;

    public static MainFragment newInstance(int position) {
        MainFragment contentFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_INT,position);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentFragment = getArguments().getInt(Constants.BUNDLE_INT);
    }

    @Override
    protected void onBackPressFragment() {
        getActivity().finish();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initViews(View view) {

        fillToScreen();

        // Set up recyclerview
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRvData.setLayoutManager(mLinearLayoutManager);
        mRvData.setHasFixedSize(true);
        mAdapter = new WallPaperAdapter(getActivity(), mListData, this);
        mRvData.setAdapter(mAdapter);
    }

    private void fillToScreen(){

        switch (mCurrentFragment){
            case 1:
                mIvBackground.setImageDrawable(mLogoCar);
                break;
            case 2:
                mIvBackground.setImageDrawable(mLogoSport);
                break;
            case 3:
                mIvBackground.setImageDrawable(mLogoFlower);
                break;
            case 4:
                mIvBackground.setImageDrawable(mLogoComics);
                break;
            case 5:
                mIvBackground.setImageDrawable(mLogoWeather);
                break;
            case 6:
                mIvBackground.setImageDrawable(mLogoDota);
                break;
            case 7:
                mIvBackground.setImageDrawable(mLogoLol);
                break;
        }

    }

    @Override
    protected void initDatas(Bundle savedInstanceState) {
    }

    @Override
    protected void getData() {

    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(mContainerView.getWidth(),
                        mContainerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                mContainerView.draw(canvas);
                mBitMap = bitmap;
            }
        };

        thread.start();
    }

    @Override
    public Bitmap getBitmap() {
        return mBitMap;
    }

    @Override
    public void onItemClick(int position, WallPaperModel item) {

    }

    @Override
    public void onItemInfo(int position, WallPaperModel item) {

    }
}
