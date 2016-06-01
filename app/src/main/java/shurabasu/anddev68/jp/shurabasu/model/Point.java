package shurabasu.anddev68.jp.shurabasu.model;

import java.util.ArrayList;

/**
 * Created by kano on 2016/06/01.
 */
public class Point {
    public int current;
    public int max;

    public Point(int current,int max){
        this.current = current;
        this.max = max;
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
