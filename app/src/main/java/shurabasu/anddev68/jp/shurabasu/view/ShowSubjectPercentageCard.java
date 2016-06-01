package shurabasu.anddev68.jp.shurabasu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import shurabasu.anddev68.jp.shurabasu.R;

/**
 * Created by kano on 2016/06/01.
 */
public class ShowSubjectPercentageCard extends LinearLayout{

    ProgressBar progressBar;

    public ShowSubjectPercentageCard(Context context) {
        super(context);
        init(context);
    }

    public ShowSubjectPercentageCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShowSubjectPercentageCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setPercentage(int current,int max){
        progressBar.setMax(max);
        progressBar.setProgress(current);
    }

    private void init(Context context){
        View v =  LayoutInflater.from(context).inflate(R.layout.card_spinner_show_subject,this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

}
