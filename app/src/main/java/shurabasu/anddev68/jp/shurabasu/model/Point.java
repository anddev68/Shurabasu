package shurabasu.anddev68.jp.shurabasu.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;

/**
 * Created by kano on 2016/06/01.
 */
@DatabaseTable(tableName = "point")
public class Point {

    @DatabaseField(generatedId=true)
    public Long id;
    @DatabaseField
    public int current;
    @DatabaseField
    public int max;
    @DatabaseField
    public String description;

    @SuppressWarnings("unused")
    private Point(){
    }

    public Point(Long id,int current,int max,String description){
        this.id = id;
        this.current = current;
        this.max = max;
        this.description = description;
    }


    public static int sumMax(ArrayList<Point> v){
        int sum = 0;
        for(Point tmp : v){
            sum += tmp.max;
        }
        return sum;
    }


    public static int sumCurrent(ArrayList<Point> v){
        int sum = 0;
        for(Point tmp : v){
            sum += tmp.current;
        }
        return sum;
    }


}
