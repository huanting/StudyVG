package com.energysh.drawshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.energysh.drawshow.activity.MainPageActivity;
import com.energysh.drawshow.activity.StudentActivity;
import com.energysh.drawshow.io.IOHelper;

/**
 * Description: This file is used to select type, teacher or student.
 * Created by anting.hu on 2015/12/21.
 */
public class ActivityType extends Activity {

    private Button mBtnTeacher;
    private Button mBtnStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        InitControl();
    }

    void InitControl()
    {
        mBtnTeacher = (Button)findViewById(R.id.btn_teacher);
        mBtnStudent = (Button)findViewById(R.id.btn_student);

        mBtnTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.mSelectType = SelectType.TEACHER;
                Intent intent = new Intent(ActivityType.this, MainActivity.class);
                intent.putExtra("paintingPath", IOHelper.createFolderUnderTeacher());
                startActivity(intent);;
            }
        });


        mBtnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.mSelectType = SelectType.STUDENT;
                Intent intent = new Intent(ActivityType.this, MainPageActivity.class);
                startActivity(intent);
            }
        });
    }
}
