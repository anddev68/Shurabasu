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
    public String className;

    /* デフォルトコンストラクタ */
    public Subject(int id, String className, String name, String url){
        this.id = id;
        this.name = name;
        this.url = url;
        this.className = className;
    }

    public static Subject find(String className,SQLiteDatabase db){
        Cursor cursor = db.rawQuery(R.string.sqls.find_subject,className);
        if(cursor==null) return null;
        return new Subject(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3) );
    }

    public static boolean save(String subject,SQLiteDatabase db){
        db.beginTransaction();
        db.execSQL();
        db.endTransaction();
    }

}
