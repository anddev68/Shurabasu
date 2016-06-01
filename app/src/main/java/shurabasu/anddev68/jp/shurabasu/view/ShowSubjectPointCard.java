package shurabasu.anddev68.jp.shurabasu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import shurabasu.anddev68.jp.shurabasu.R;

/**
 * Created by kano on 2016/06/01.
 */
public class ShowSubjectPointCard extends LinearLayout{

    public ShowSubjectPointCard(Context context) {
        super(context);
        init(context);
    }

    public ShowSubjectPointCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShowSubjectPointCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
       View v =  LayoutInflater.from(context).inflate(R.layout.card_point_show_subject,this);
    }

}
