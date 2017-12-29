package com.doanhtu.changewallpaper.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.doanhtu.changewallpaper.R;
import com.doanhtu.changewallpaper.model.WallPaperModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by doanhtu on 12/28/17.
 */

public class WallPaperAdapter extends RecyclerView.Adapter<WallPaperAdapter.ViewHolder> {

    private Context mContext;
    private ICallBackItemClick mListener;
    private List<WallPaperModel> mListData;

    public WallPaperAdapter(Context context, List<WallPaperModel> listData, ICallBackItemClick listener) {
        this.mContext = context;
        this.mListener = listener;
        this.mListData = listData;
    }

    public interface ICallBackItemClick {
        void onItemClick(int position, WallPaperModel item);

        void onItemInfo(int position, WallPaperModel item);
    }

    @Override
    public int getItemCount() {
        return mListData != null ? mListData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void setData(WallPaperModel model) {
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_wall_paper, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mListData.get(position));
    }

}
