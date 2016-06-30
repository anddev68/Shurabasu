package shurabasu.anddev68.jp.shurabasu.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Created by chrome on 16/06/10.
 */
public class MyDatabaseHelper extends OrmLiteSqliteOpenHelper {

    final static String DATABASE_NAME = "shurabasu.db";
    final static int DATABASE_VERSION = 2;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        System.out.println("MyDatabaseHelper.onCreate()");
        try {
            TableUtils.createTable(connectionSource, Point.class);
            TableUtils.createTable(connectionSource, Subject.class);
            TableUtils.createTable(connectionSource, MyClass.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            if(oldVersion==1)
                TableUtils.createTable(connectionSource, MyClass.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
}
