package shurabasu.anddev68.jp.shurabasu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;
import java.util.ArrayList;

import shurabasu.anddev68.jp.shurabasu.model.MyDatabaseHelper;
import shurabasu.anddev68.jp.shurabasu.model.Subject;
import shurabasu.anddev68.jp.shurabasu.parser.GnctParser;

/**
 * Created by chrome on 16/06/09.
 */
public class RegisterActivity extends AppCompatActivity {

    MyDatabaseHelper mDatabaseHelper;
    RuntimeExceptionDao<Subject,Long> mSubjectDao;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_register);
        mDatabaseHelper = OpenHelperManager.getHelper(this, MyDatabaseHelper.class);
        mSubjectDao = mDatabaseHelper.getRuntimeExceptionDao(Subject.class);

        if( getIntent().getBooleanExtra("first-execution",false)){
            //  first execution
            //  download list
            onFirstExecution();
        }else{


        }



    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        OpenHelperManager.releaseHelper();
    }

    /**
     * call by onCreate
     * if first execution
     *
     * get subject list
     *
     */
    private void onFirstExecution(){
        getSupportLoaderManager().initLoader(0,null,new MyLoaderCallback());
    }



    /**
     * call by pressed button
     * add ids that match with className into my_class table
     */
    private void onPressedRegisterButton(){

    }


    private void openDownloadingDialog(){

    }


    private void closeDownloadingDialog(){

    }



    private class MyLoaderCallback implements LoaderCallbacks<ArrayList<Subject>> {

        @Override
        public Loader<ArrayList<Subject>> onCreateLoader(int id, Bundle args) {
            openDownloadingDialog();
            return new MyTaskLoader(RegisterActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<Subject>> loader, ArrayList<Subject> data) {
            //  save subject data into database
            for(Subject tmp : data) {
                mSubjectDao.create(tmp);
                System.out.println(tmp);
            }
            closeDownloadingDialog();
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<Subject>> loader) {

        }
    }



    private static class MyTaskLoader extends AsyncTaskLoader<ArrayList<Subject>>{

        public MyTaskLoader(Context context) {
            super(context);
        }

        @Override
        public ArrayList<Subject> loadInBackground() {
            GnctParser parser = new GnctParser("EE","http://www.gifu-nct.ac.jp/syllabus/BrowsingPage/E/EindexEEcourse.html");
            return parser.parse();
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }
    }







}
