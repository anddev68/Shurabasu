package shurabasu.anddev68.jp.shurabasu;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import shurabasu.anddev68.jp.shurabasu.model.Subject;

/**
 * MainAdapter
 * MainActivityに表示する教科一覧になります
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    Context mContext;
    LayoutInflater mLayoutInflater;
    List<Subject> mData;

    public MainAdapter(Context context, List<Subject> data){
        super();
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = mLayoutInflater.inflate(R.layout.row_main, parent, false);

        ViewHolder viewHolder = new ViewHolder(v,mContext);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        /* 値を代入したりする処理 */
        holder.textView.setText(mData.get(position).name);
        holder.iconTextView.setText(mData.get(position).className);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }





    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Context context;
        View rootView;
        TextView textView;
        TextView iconTextView;

        public ViewHolder(View v,Context context) {
            super(v);
            rootView = v;
            textView = (TextView) v.findViewById(R.id.text);
            iconTextView = (TextView) v.findViewById(R.id.iconText);

            this.context = context;

        }


        @Override
        public void onClick(View view) {
            /* 押されたときの処理 */

        }
    }

}
