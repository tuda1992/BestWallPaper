package com.doanhtu.changewallpaper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.doanhtu.changewallpaper.adapter.WallPaperAdapter;
import com.doanhtu.changewallpaper.base.BaseFragment;
import com.doanhtu.changewallpaper.model.WallPaperModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by doanhtu on 12/28/17.
 */

public class MainFragment extends BaseFragment implements ScreenShotable, WallPaperAdapter.ICallBackItemClick {

    public static final String CLOSE = "Close";
    public static final String CAR = "Car";
    public static final String MOTOR_BIKE = "Motorbike";
    public static final String FOOT_BALL = "FootBall";
    public static final String FLOWER = "Flower";
    public static final String COMICS = "Comics";
    public static final String WEATHER = "Weather";
    public static final String DOTA = "Dota";
    public static final String LOL = "Lol";

    private Bitmap mBitMap;
    private WallPaperAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<WallPaperModel> mListData = new ArrayList<>();

    @BindView(R.id.container_frame)
    View mContainerView;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;

    public static MainFragment newInstance() {
        MainFragment contentFragment = new MainFragment();
        Bundle bundle = new Bundle();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        // Set up recyclerview
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRvData.setLayoutManager(mLinearLayoutManager);
        mRvData.setHasFixedSize(true);
        mAdapter = new WallPaperAdapter(getActivity(), mListData, this);
        mRvData.setAdapter(mAdapter);
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
