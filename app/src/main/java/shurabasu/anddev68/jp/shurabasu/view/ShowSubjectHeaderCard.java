package shurabasu.anddev68.jp.shurabasu.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import shurabasu.anddev68.jp.shurabasu.R;

/**
 * Created by Administrator on 2016/06/01.
 */
public class ShowSubjectHeaderCard extends CardView{
    public ShowSubjectHeaderCard(Context context) {
        super(context);
        init(context);
    }

    public ShowSubjectHeaderCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShowSubjectHeaderCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        View v =  LayoutInflater.from(context).inflate(R.layout.card_header_show_subject,this);
        final CircularProgressView progressView = (CircularProgressView) findViewById(R.id.progress_view);
        //progressView.startAnimation();
        //progressView.stopAnimation();


        progressView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //progressView.stopAnimation();
                progressView.setProgress(progressView.getProgress() + 10);
            }
        });

    }
}
