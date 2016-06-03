package shurabasu.anddev68.jp.shurabasu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import shurabasu.anddev68.jp.shurabasu.model.Point;
import shurabasu.anddev68.jp.shurabasu.view.ShowSubjectHeaderCard;
import shurabasu.anddev68.jp.shurabasu.view.ShowSubjectPointCard;

/**
 * Created by kano on 2016/06/01.
 */
public class ShowSubjectAdapter extends  RecyclerView.Adapter<ShowSubjectAdapter.ViewHolder>{

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<Point> mData;

    final static int VIEW_HEADER = 0;
    final static int VIEW_POINT = 1;

    public ShowSubjectAdapter(Context context, ArrayList<Point> data){
        super();
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        switch(viewType){
            case VIEW_POINT:
                v = new ShowSubjectPointCard(parent.getContext());
                break;
            case VIEW_HEADER:
                v =  new ShowSubjectHeaderCard(parent.getContext());
                break;
        }
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        /* 値を代入したりする処理 */
        int type = holder.getItemViewType();
        switch(type){
            case VIEW_POINT:
                break;
            case VIEW_HEADER:
                break;

        }
    }


    @Override
    public int getItemViewType(int position) {
        /* 0番目は割合 */
        /* それ以外は点数 */
        switch(position){
            case 0: return VIEW_HEADER;
            case 1: return VIEW_POINT;
        }

        return VIEW_POINT;
    }


    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }





    static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View v) {
            super(v);
        }
    }


}
