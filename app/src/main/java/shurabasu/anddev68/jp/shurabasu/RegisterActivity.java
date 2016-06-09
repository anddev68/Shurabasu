package shurabasu.anddev68.jp.shurabasu;

import android.content.AsyncTaskLoader;
import android.os.Bundle;
import android.widget.Spinner;

import java.util.ArrayList;

import shurabasu.anddev68.jp.shurabasu.model.Subject;

/**
 * Created by chrome on 16/06/09.
 */
public class RegisterActivity extends AppCompatActivity {


    @Override
    public onCreate(Bundle bundle){
        super.onCreate(bundle);
    }


    /**
     * call by pressed button
     * add ids that match with className into my_class table
     */
    private void onPressedRegisterButton(){
        Spinner spinner = null;
        String className = spinner.getSelectedItem().toString();
        ArrayList<Subject> subjects = null;

        //  add subject into my_class table
        for( Subject subject : subjects ){
            Subject.save(subject,db);
        }

        //  notice


    }



    private void openDownloadingDialog(){

    }


    private void closeDownloadingDialog(){

    }





}
