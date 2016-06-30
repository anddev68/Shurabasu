package shurabasu.anddev68.jp.shurabasu.parser;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import shurabasu.anddev68.jp.shurabasu.model.Subject;

/**
 * html parser of gnct
 */
public class GnctParser {

    String mClassName;
    String mUrl;

    private final static String[] EXCLUDING_FROM_SUBJECTS = {"教育目標","科目構成図","科目構成図"};
    //private final static Pattern PATTERN = Pattern.compile("[0-9]([0-9])[0-9]+[.]pdf");

    public GnctParser(String className, String url){
        mClassName = className;
        mUrl = url;
    }


    public ArrayList<Subject> parse() {
        switch (mClassName){
            case "C":
                return parseC();
            case "D":
                return parseD();
            default:
                return parseM();
        }

    }


    /**
     * C科専用のパーサー
     * C科はaタグ直にpdfリンクと科目名が入っている
     * @return
     */
    private ArrayList<Subject> parseC(){
        ArrayList<Subject> subjects = new ArrayList<>();
        try {
            Document document = Jsoup.connect(mUrl).get();
            Elements elements = document.getElementsByTag("td");

            for(Element elem : elements){
                //  get A tag
                Elements a_tag = elem.getElementsByTag("a");
                //  parse A tag
                String pdf_name = a_tag.attr("href");
                String subject_name = a_tag.text();
                //  教科名でないもの、から文字を除外する
                boolean excluding = pdf_name.length()==0;
                for(String exclude : EXCLUDING_FROM_SUBJECTS){
                    if( subject_name.contains(exclude)){
                        excluding = true;
                    }
                }
                //  教科名と判断された場合のみ追加する
                if(!excluding){
                    int grade = pdf_name.charAt(1) - '0';
                    String class_name = grade + mClassName;
                    String url = mUrl.substring(0,mUrl.lastIndexOf('/'))+"/" + pdf_name; //  URLは絶対パス
                    subjects.add(new Subject(1L,class_name,subject_name,url));
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return subjects;
    }

    /**
     * D科専用のパーサー
     * B,C,Dを応用数学B,C,Dに置換
     * @return
     */
    private ArrayList<Subject> parseD(){
        ArrayList<Subject> subjects = new ArrayList<>();
        try {
            Document document = Jsoup.connect(mUrl).get();
            Elements elements = document.getElementsByTag("td");

            for(Element elem : elements){
                //  get A tag
                Elements a_tag = elem.getElementsByTag("a");
                //  parse A tag
                String pdf_name = a_tag.attr("href");
                String subject_name =a_tag.text();

                //  教科名でないもの、から文字を除外する
                boolean excluding = pdf_name.length()==0;
                for(String exclude : EXCLUDING_FROM_SUBJECTS){
                    if( subject_name.contains(exclude)){
                        excluding = true;
                    }
                }
                //  教科名と判断された場合のみ追加する
                if(!excluding){
                    // B,C,D 置換処理
                    subject_name = subject_name.replace("B","応用数学B");
                    int grade = pdf_name.charAt(1) - '0';
                    String class_name = grade + mClassName;
                    String url = mUrl.substring(0,mUrl.lastIndexOf('/'))+"/" + pdf_name; //  URLは絶対パス
                    subjects.add(new Subject(1L,class_name,subject_name,url));
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return subjects;
    }


    /**
     * それ以外のパーサー
     * @return
     */
    private ArrayList<Subject> parseM(){
        ArrayList<Subject> subjects = new ArrayList<>();
        try {
            Document document = Jsoup.connect(mUrl).get();
            Elements elements = document.getElementsByTag("td");

            for(Element elem : elements){
                //  get A tag
                Elements a_tag = elem.getElementsByTag("a");
                //  parse A tag
                String pdf_name = a_tag.attr("href");
                String subject_name =a_tag.text();

                //  教科名でないもの、から文字を除外する
                boolean excluding = pdf_name.length()==0;
                for(String exclude : EXCLUDING_FROM_SUBJECTS){
                    if( subject_name.contains(exclude)){
                        excluding = true;
                    }
                }
                //  教科名と判断された場合のみ追加する
                if(!excluding){
                    int grade = pdf_name.charAt(1) - '0';
                    String class_name = grade + mClassName;
                    String url = mUrl.substring(0,mUrl.lastIndexOf('/'))+"/" + pdf_name; //  URLは絶対パス
                    subjects.add(new Subject(1L,class_name,subject_name,url));
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return subjects;
    }



}
