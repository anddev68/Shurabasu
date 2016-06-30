package shurabasu.anddev68.jp.shurabasu.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2016/05/30.
 */
@DatabaseTable(tableName = "Subject")
public class Subject {

    @DatabaseField(generatedId = true)
    public Long id;
    @DatabaseField(uniqueCombo = true)
    public String name;
    @DatabaseField
    public String url; //  シラバスへのurl
    @DatabaseField(uniqueCombo = true, columnName = "class_name")
    public String className;

    @SuppressWarnings("unused")
    private Subject(){}

    /* デフォルトコンストラクタ */
    public Subject(Long id, String className, String name, String url){
        this.id = id;
        this.name = name;
        this.url = url;
        this.className = className;
    }

    @Override
    public String toString(){
        return String.format("%3d,%s,%s,%s",id,className,name,url);
    }


}
