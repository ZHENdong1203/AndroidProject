package com.example.myapp;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.text.Selection;
import android.text.Spannable;

import android.view.View;
import android.view.Window;

import android.widget.Button;
import android.widget.EditText;



import java.text.SimpleDateFormat;
import java.util.Date;

public class EditNoteActivity extends Activity implements View.OnClickListener {
    private EditText titleEt;
    private EditText contentEt;
    private Button saveButton;
    private int noteID = -1;
    private DBManager dbManager;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        setContentView(R.layout.edit_text);
        init();
    }

    //初始化
    private void init() {
        dbManager = new DBManager(this);
        titleEt = (EditText) findViewById(R.id.note_title);
        contentEt = (EditText) findViewById(R.id.note_content);
        //saveBtn = (FloatingActionButton) findViewById(R.id.save);
        saveButton=(Button)findViewById(R.id.note_save);
        saveButton.setOnClickListener(this);

        backButton=(Button)findViewById(R.id.note_back);
        backButtonListener backButtonListener=new backButtonListener();
        backButton.setOnClickListener(backButtonListener);
        //name，defaultValue
        noteID = getIntent().getIntExtra("id", -1);
        if (noteID != -1) {
            showNoteData(noteID);
        }

    }



    //显示更新的数据
    private void showNoteData(int id) {
        Note note = dbManager.readData(id);
        titleEt.setText(note.getTitle());
        contentEt.setText(note.getContent());
        //控制光标
        Spannable spannable = titleEt.getText();
        Selection.setSelection(spannable, titleEt.getText().length());
    }

    @Override
    public void onClick(View view) {
        String title = titleEt.getText().toString();
        String content = contentEt.getText().toString();
        String time = getTime();
        if (noteID == -1) {
            //默认添加
            dbManager.addToDB(title, content, time);
        } else {
            //更新
            dbManager.updateNote(noteID, title, content, time);
        }
        Intent i = new Intent(EditNoteActivity.this, MainActivity.class);
        startActivity(i);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        this.finish();
    }

    //得到时间
    private String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm E");
        Date curDate = new Date();
        String str = format.format(curDate);
        return str;
    }



    class backButtonListener implements View.OnClickListener {
        //按返回键时


        @Override
        public void onClick(View v) {
            //Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
            //startActivity(intent);
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
        }
    }



}
