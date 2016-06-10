package shurabasu.anddev68.jp.shurabasu.parser;

import android.util.Log;

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

    public GnctParser(String className, String url){
        mClassName = className;
        mUrl = url;
    }


    public ArrayList<Subject> parse() {
        ArrayList<Subject> subjects = new ArrayList<>();
        try {
            URL url = new URL(mUrl);
            InputStream is = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"sjis"));
            String line =  null;
            CharSequence[] cs = {"１年","２年","３年","４年","５年"};
            String[] cs2 = {"地理(","英語C(","政治経済(","英語A("};
            int grade = 0;

            Log.i("GnctParser","URL:"+mUrl);

            while((line=br.readLine())!=null) {
                //  学年別に読む処理
                if( grade<cs.length) {
                    if (line.contains(cs[grade])) grade++;
                    if (grade == 0) continue;
                }

                Pattern pattern = Pattern.compile("<a.*?href=\"(.*?)\".*?>(.*?)</a>");
                Matcher m = pattern.matcher(line);
                while(m.find()){
                    String abs_path = mUrl.substring(0,mUrl.lastIndexOf('/'))+"/";
                    //String code = m.group(1).replaceAll("\\s", "").replace(".pdf","");  //  xxxxxx.pdfのxxxx部分をコードとする
                    String href = abs_path + m.group(1).replaceAll("\\s", "");  //  URLは絶対パス
                    String text = m.group(2).replaceAll("\\s", "");  // 科目名

                    //  一般科の特殊なものを処理
                    for(String str:cs2){
                        if(line.contains(str)){
                            text = str+text+")";
                        }
                    }

                    //Log.i("GnctParser",
                    //    String.format("grade=%d href=%s text=%s",grade,href,text));
                    //if(! _listener.onParsedLine(text,href,code,grade) ){
                        //  終了処理を加える
                    //}
                    Subject subject = new Subject(1L,grade+mClassName,text,href);
                    subjects.add(subject);
                }

            }

            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return subjects;
    }




}
