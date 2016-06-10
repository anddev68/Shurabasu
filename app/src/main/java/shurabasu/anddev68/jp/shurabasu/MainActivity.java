package shurabasu.anddev68.jp.shurabasu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import shurabasu.anddev68.jp.shurabasu.model.MyDatabaseHelper;
import shurabasu.anddev68.jp.shurabasu.model.Subject;

public class MainActivity extends AppCompatActivity {

    MyDatabaseHelper mDatabaseHelper;
    RuntimeExceptionDao<Subject,Long> mSubjectDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        mDatabaseHelper = OpenHelperManager.getHelper(this, MyDatabaseHelper.class);
        mSubjectDao = mDatabaseHelper.getRuntimeExceptionDao(Subject.class);

        if( mSubjectDao.idExists(1L) ){
            //   is not first execution
            initRecyclerAdapter();

        }else{
            //  first execution
            //onFirstExecution();
            Intent intent = new Intent(this,RegisterActivity.class);
            intent.putExtra("first-execution",true);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        OpenHelperManager.releaseHelper();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initRecyclerAdapter(){
        try {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            QueryBuilder<Subject, Long> queryBuilder = mSubjectDao.queryBuilder();
            PreparedQuery<Subject> preparedQuery = queryBuilder.prepare();
            List<Subject> list = mSubjectDao.query(preparedQuery);
            MainAdapter mainAdapter = new MainAdapter(this,list);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(mainAdapter);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
