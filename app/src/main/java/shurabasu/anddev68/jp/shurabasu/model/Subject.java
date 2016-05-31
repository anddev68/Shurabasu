package shurabasu.anddev68.jp.shurabasu.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2016/05/30.
 */
public class Subject {

    public int id;
    public String name;
    public String url; //  シラバスへのurl
    public int gradeId;
    public int departId;

    SQLiteDatabase db;
    Context context;

    public Subject(SQLiteDatabase db,Context context){
        this.db = db;
        this.context = context;
    }

    /* デフォルトコンストラクタ */
    public Subject(String name,String url,int grade,int departId){
        this.id = -1;
        this.name = name;
        this.url = url;
        this.gradeId = grade;
        this.departId = departId;
    }

    /* Databaseからcursorで初期化 */
    public Subject(Cursor cursor){
        this.id = cursor.getInt(0);
        this.name = cursor.getString(1);
        this.url = cursor.getString(2);
        this.gradeId = cursor.getInt(3);
        this.departId = cursor.getInt(4);
    }



}
