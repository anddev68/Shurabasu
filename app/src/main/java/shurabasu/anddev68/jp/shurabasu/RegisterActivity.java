package shurabasu.anddev68.jp.shurabasu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import shurabasu.anddev68.jp.shurabasu.model.MyClass;
import shurabasu.anddev68.jp.shurabasu.model.MyDatabaseHelper;
import shurabasu.anddev68.jp.shurabasu.model.Subject;
import shurabasu.anddev68.jp.shurabasu.parser.GnctParser;

/**
 * Created by chrome on 16/06/09.
 */
public class RegisterActivity extends AppCompatActivity {

    MyDatabaseHelper mDatabaseHelper;
    RuntimeExceptionDao<Subject,Long> mSubjectDao;
    RuntimeExceptionDao<MyClass,Long> mMyClassDao;
    AlertDialog mDownloadingDialog;

    final String[] GRADES ={"1", "2", "3", "4", "5"};
    final String[] DEPARTMENTS = {"M","EE","EJ","D","C","A","Y"};
    final String[] DEPARTMENTS2 = {"M","EE","EJ","D","C","A","S","K"};
    final String[] DEPARTMENTS3 = {"M","EE","EJ","D","C","A"};

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //  spinner setting
        final MaterialSpinner spinner1 = (MaterialSpinner) findViewById(R.id.spinner);
        final MaterialSpinner spinner2 = (MaterialSpinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, GRADES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                //  change selection of spinner2 by spinner1
                ArrayAdapter<String> adapter = null;
                if(pos==0) adapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DEPARTMENTS);
                else if(pos==1) adapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DEPARTMENTS2);
                else adapter = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, DEPARTMENTS3);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //  button settings
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  register my_class
                try {
                    String className = String.format("%s%s", spinner1.getSelectedItem().toString(), spinner2.getSelectedItem().toString());
                    System.out.println(className);
                    QueryBuilder<Subject, Long> queryBuilder = mSubjectDao.queryBuilder();
                    PreparedQuery<Subject> preparedQuery = queryBuilder.where().eq("class_name", className).prepare();
                    List<Subject> list = mSubjectDao.query(preparedQuery);
                    for(Subject tmp : list){
                        System.out.println(tmp);
                        mMyClassDao.create(new MyClass(1L,tmp.id));
                    }
                    setResult(RESULT_OK);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        mDatabaseHelper = OpenHelperManager.getHelper(this, MyDatabaseHelper.class);
        mSubjectDao = mDatabaseHelper.getRuntimeExceptionDao(Subject.class);
        mMyClassDao = mDatabaseHelper.getRuntimeExceptionDao(MyClass.class);

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
        openDownloadingDialog();
        getSupportLoaderManager().initLoader(0,null,new MyLoaderCallback());
    }



    /**
     * call by pressed button
     * add ids that match with className into my_class table
     */
    private void onPressedRegisterButton(){

    }


    /**
     * operate dialog
     * called, if downloading
     */
    private void openDownloadingDialog(){
        mDownloadingDialog = new AlertDialog.Builder(this)
                .setTitle("Wait for downloading")
                .setMessage("初期起動は時間がかかる場合があります。")
                .show();
    }
    private void closeDownloadingDialog(){
        mDownloadingDialog.dismiss();
    }






    private class MyLoaderCallback implements LoaderCallbacks<ArrayList<Subject>> {

        @Override
        public Loader<ArrayList<Subject>> onCreateLoader(int id, Bundle args) {
            return new MyTaskLoader(RegisterActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<Subject>> loader, ArrayList<Subject> data) {
            //  save subject data into database
            for(Subject tmp : data) {
                mSubjectDao.create(tmp);
                //System.out.println(tmp);
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
            ArrayList<Subject> list = new ArrayList<>();
            //  GnctParser parser = new GnctParser("EE","http://www.gifu-nct.ac.jp/syllabus/BrowsingPage/E/EindexEEcourse.html");
            list.addAll(new GnctParser("M","http://www.gifu-nct.ac.jp/syllabus/BrowsingPage/M/Mindex.html").parse());
            list.addAll(new GnctParser("D","http://www.gifu-nct.ac.jp/syllabus/BrowsingPage/D/Mindex.html").parse());
            list.addAll(new GnctParser("C","http://www.gifu-nct.ac.jp/syllabus/BrowsingPage/C/Mindex.html").parse());
            list.addAll(new GnctParser("A","http://www.gifu-nct.ac.jp/syllabus/BrowsingPage/A/Mindex.html").parse());
            list.addAll(new GnctParser("EE","http://www.gifu-nct.ac.jp/syllabus/BrowsingPage/E/EindexEEcourse.html").parse());
            list.addAll(new GnctParser("EE","http://www.gifu-nct.ac.jp/syllabus/BrowsingPage/E/EindexEJcourse.html").parse());
            //list.addAll(new GnctParser("E","http://www.gifu-nct.ac.jp/syllabus/BrowsingPage/E/EindexEEcourse").parseM());
            //list.addAll(new GnctParser("C","http://www.gifu-nct.ac.jp/syllabus/BrowsingPage/C/Cindex.html").parse());
            return list;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }
    }







}
