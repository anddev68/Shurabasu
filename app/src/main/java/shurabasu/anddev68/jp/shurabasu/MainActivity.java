package shurabasu.anddev68.jp.shurabasu;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import shurabasu.anddev68.jp.shurabasu.model.MyClass;
import shurabasu.anddev68.jp.shurabasu.model.MyDatabaseHelper;
import shurabasu.anddev68.jp.shurabasu.model.Subject;

public class MainActivity extends AppCompatActivity {

    MyDatabaseHelper mDatabaseHelper;
    RuntimeExceptionDao<Subject,Long> mSubjectDao;
    RuntimeExceptionDao<MyClass,Long> mMyClassDao;
    MainAdapter mCurrentRecyclerViewAdapter;
    List<Subject> mCurrentMainAdapterSubjects;

    public final static int REQUEST_REGISTER_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                // スケールアップ用 ActivityOptions をインスタンス化
                ActivityOptions opts = ActivityOptions.makeScaleUpAnimation(
                        fab, 0, 0, fab.getWidth(), fab.getHeight());
                // アニメーションを指定してアクティビティを起動
                startActivityForResult(intent, REQUEST_REGISTER_ACTIVITY, opts.toBundle());
            }
        });


        mDatabaseHelper = OpenHelperManager.getHelper(this, MyDatabaseHelper.class);
        mSubjectDao = mDatabaseHelper.getRuntimeExceptionDao(Subject.class);
        mMyClassDao = mDatabaseHelper.getRuntimeExceptionDao(MyClass.class);

        if( mSubjectDao.idExists(1L) ){
            //   is not first execution
            initRecyclerAdapter();

        }else{
            //  first execution
            //onFirstExecution();
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            // スケールアップ用 ActivityOptions をインスタンス化
            ActivityOptions opts = ActivityOptions.makeScaleUpAnimation(
                    fab, 0, 0, fab.getWidth(), fab.getHeight());
            // アニメーションを指定してアクティビティを起動
            startActivityForResult(intent, REQUEST_REGISTER_ACTIVITY, opts.toBundle());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_REGISTER_ACTIVITY){
            switch(resultCode){
                case RESULT_OK:
                    initRecyclerAdapter();
                    Toast.makeText(this, "データが追加されました。", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "ユーザによりキャンセルされました。", Toast.LENGTH_SHORT).show();
                    break;
            }

        }

    }

    private void initRecyclerAdapter(){
        try {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            //   GenericRawResults<String[]> result = mSubjectDao.queryRaw("SELECT * FROM subject WHERE subject.id IN (SELECT id FROM my_class);");
            QueryBuilder<Subject, Long> queryBuilder = mSubjectDao.queryBuilder();
            QueryBuilder<MyClass,Long> subBuilder = mMyClassDao.queryBuilder().selectColumns("subject_id");
            PreparedQuery<Subject> preparedQuery = queryBuilder.where().in("id",subBuilder).prepare();
            mCurrentMainAdapterSubjects = mSubjectDao.query(preparedQuery);
            mCurrentRecyclerViewAdapter = new MainAdapter(this,mCurrentMainAdapterSubjects);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(mCurrentRecyclerViewAdapter);
            ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {

                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    Log.d("", "OnMove");
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    Log.d("", "OnSwiped");
                    onSwipedRecyclerItem(viewHolder.getAdapterPosition());
                }
            });
            helper.attachToRecyclerView(recyclerView);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void onSwipedRecyclerItem(int pos){
        try {
            //  delete my_class
            DeleteBuilder<MyClass, Long> deleteBuilder = mMyClassDao.deleteBuilder();
            deleteBuilder.where().eq("subject_id", mCurrentMainAdapterSubjects.get(pos).id);
            deleteBuilder.delete();
            //  delete data and update adapter
            mCurrentMainAdapterSubjects.remove(pos);
            mCurrentRecyclerViewAdapter.notifyDataSetChanged();

        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
