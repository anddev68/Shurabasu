package shurabasu.anddev68.jp.shurabasu.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import shurabasu.anddev68.jp.shurabasu.R;

/**
 * Created by Administrator on 2016/06/01.
 */
public class ShowSubjectSliderCard extends CardView{
    public ShowSubjectSliderCard(Context context) {
        super(context);
        init(context);
    }

    public ShowSubjectSliderCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShowSubjectSliderCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        View v =  LayoutInflater.from(context).inflate(R.layout.card_slider_show_subject,this);
    }
}
