package shurabasu.anddev68.jp.shurabasu.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2016/06/11.
 */
@DatabaseTable(tableName = "my_class")
public class MyClass {
    @DatabaseField(generatedId = true)
    public Long id;
    @DatabaseField(columnName = "subject_id", unique = true)
    public Long subjectId;

    @SuppressWarnings("unused")
    private MyClass(){}

    public MyClass(Long id,Long subjectId){
        this.id = id;
        this.subjectId = subjectId;
    }

}
