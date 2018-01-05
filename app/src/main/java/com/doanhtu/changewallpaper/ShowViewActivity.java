package com.doanhtu.changewallpaper;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.doanhtu.changewallpaper.base.BaseActivity;
import com.doanhtu.changewallpaper.constant.Constants;
import com.doanhtu.changewallpaper.customview.TouchImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by doanhtu on 1/3/18.
 */

public class ShowViewActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.rl_content)
    RelativeLayout mRlContent;
    @BindView(R.id.iv_wall_paper)
    TouchImageView mIvWallPaper;

    private Bitmap mBmWalPaper = null;

    @OnClick(R.id.fab_change)
    public void onClickChangeWallPaper() {
        Log.d(TAG, "onClickChangeWallPaper");
        if (mBmWalPaper != null) {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            // get the height and width of screen
            int height = metrics.heightPixels;
            int width = metrics.widthPixels;

            WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
            try {
                wallpaperManager.setBitmap(mBmWalPaper);

                wallpaperManager.suggestDesiredDimensions(width, height);
                Toast.makeText(this, "Change Wallpaper Complete", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.fab_download)
    public void onClickDownloadWallPaper() {
        Log.d(TAG, "onClickDownloadWallPaper");
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_show_view;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finishActivityAnim();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivityAnim();
    }

    @Override
    protected void initDatas(Bundle saveInstanceState) {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            String url = b.getString(Constants.BUNDLE_URL);
            String title = b.getString(Constants.BUNDLE_TITLE);

            getSupportActionBar().setTitle(title);

            Picasso.with(this).load(url).into(new Target() {
                @Override
                public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                    mIvWallPaper.setImageBitmap(bitmap);
                    mBmWalPaper = bitmap;
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    mBmWalPaper = null;
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    mBmWalPaper = null;
                }
            });
        }
    }

    @Override
    protected void getData() {

    }
}
