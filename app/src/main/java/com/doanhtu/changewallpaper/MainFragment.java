package com.doanhtu.changewallpaper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.androidnetworking.error.ANError;
import com.doanhtu.changewallpaper.adapter.WallPaperAdapter;
import com.doanhtu.changewallpaper.base.BaseFragment;
import com.doanhtu.changewallpaper.constant.Constants;
import com.doanhtu.changewallpaper.customview.EndlessRecyclerViewScrollListener;
import com.doanhtu.changewallpaper.customview.GridSpacingItemDecoration;
import com.doanhtu.changewallpaper.customview.TouchImageView;
import com.doanhtu.changewallpaper.model.ModelHelper;
import com.doanhtu.changewallpaper.model.ResultWallPaper;
import com.doanhtu.changewallpaper.model.UserLogin;
import com.doanhtu.changewallpaper.model.WallPaper;
import com.doanhtu.changewallpaper.networking.FastConnection;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;
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
    private GridLayoutManager mGridLayoutManager;
    private List<WallPaper> mListData = new ArrayList<>();
    private int mCurrentFragment;
    private EndlessRecyclerViewScrollListener mLoadMoreListener;
    private String mToken;

    @BindView(R.id.container_frame)
    View mContainerView;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.iv_background)
    ImageView mIvBackground;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefresh;

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
        bundle.putInt(Constants.BUNDLE_INT, position);
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
        mLoadMoreListener = new EndlessRecyclerViewScrollListener(mGridLayoutManager) {
            @Override
            public void onLoadMore(int page) {

            }
        };

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callApi(mToken);
            }
        });

    }

    @Override
    protected void initViews(View view) {
        fillToScreen();
        // Set up recyclerview
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRvData.setLayoutManager(mGridLayoutManager);
        mRvData.setHasFixedSize(true);
//        mRvData.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mAdapter = new WallPaperAdapter(getActivity(), mListData, this);
        mRvData.setAdapter(mAdapter);
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void fillToScreen() {

        switch (mCurrentFragment) {
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
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", "tuda19922@gmail.com");
            jsonObject.put("password", "123456");
            jsonObject.put("version_app", "1.0.0");
            jsonObject.put("os", "android");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        callApiLogin(jsonObject);
    }

    private void callApiLogin(JSONObject jsonObject) {
        String endUrl = "auth/login";
        FastConnection fastConnection = new FastConnection(getActivity(), new FastConnection.CallBackResponseListener() {
            @Override
            public void onResponeSuccess(String jsonObject) {
                Log.d(TAG, "JSONObject : " + jsonObject);
                UserLogin userLogin = ModelHelper.getInstance().getUserLogin(jsonObject);
                mToken = userLogin.token;
                callApi(mToken);
            }

            @Override
            public void onError(ANError anError) {
                Log.d(TAG, "ANError : " + anError.getErrorBody().toString());
            }
        });

        fastConnection.callApi(endUrl, jsonObject);
    }

    private void callApi(String token) {
        HashMap<String, String> mMapData = new HashMap<>();
        mMapData.put("page", "1");
        FastConnection fastConnection = new FastConnection(getActivity(), new FastConnection.CallBackResponseListener() {
            @Override
            public void onResponeSuccess(String json) {
                mSwipeRefresh.setRefreshing(false);
                mListData.clear();
                Gson gson = new Gson();
                Log.d(TAG, "onResponeSuccess : " + json);
                ResultWallPaper resultWallPaper = gson.fromJson(json, ResultWallPaper.class);
                mListData.addAll(resultWallPaper.list);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(ANError anError) {
                mSwipeRefresh.setRefreshing(false);
                Log.d(TAG, "ANError : " + anError.getErrorBody().toString());
            }
        });
        fastConnection.callApiMethodGETRequestList("request/list", token, mMapData);
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
    public void onItemClick(int position, WallPaper item) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_TITLE, item.titleWallPaper);
        bundle.putString(Constants.BUNDLE_URL, item.urlWallPaper);
        startActivityAnim(ShowViewActivity.class, bundle);
    }

    @Override
    public void onItemInfo(int position, WallPaper item) {

    }
}
