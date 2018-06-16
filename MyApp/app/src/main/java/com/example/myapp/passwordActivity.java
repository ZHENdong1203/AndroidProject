package com.example.myapp;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.widget.Toast;

public class passwordActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener{
    private Button password_back;
    private EditText et_orgin;
    private EditText et_new;
    private EditText et_confirm;
    private Button bt_confirm;
    private ProgressDialog dialog;
    private MyDBHelper mydbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.password_layout);
        password_back=(Button)findViewById(R.id.password_back_btn);
        passwordCloseListener passwordCloseListener=new passwordCloseListener();
        password_back.setOnClickListener(passwordCloseListener);
        mydbHelper = new MyDBHelper(this,"UserData.db",null,1);
        initView();
        initData();

    }

    class passwordCloseListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            finish();
        }
    }

    private void initView() {
        et_orgin= (EditText) findViewById(R.id.id_orgin_et);
        et_confirm= (EditText) findViewById(R.id.id_confirm_et);
        et_new= (EditText) findViewById(R.id.id_new_et);
        bt_confirm= (Button) findViewById(R.id.id_confirm_bt);
        et_confirm.addTextChangedListener(this);
        et_orgin.addTextChangedListener(this);
        et_new.addTextChangedListener(this);
        bt_confirm.setSelected(false);
        bt_confirm.setOnClickListener(this);
        et_orgin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clearAll();
                return false;
            }
        });

        et_new.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clearAll();
                return false;
            }
        });

        et_confirm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clearAll();
                return false;
            }
        });
    }
    private void initData() {
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
    @Override
    public void onClick(View v) {
        if (checkNull()) {
            return;
        }
        if (!et_confirm.getText().toString().equals(et_new.getText().toString())) {
            et_confirm.setText("");
            requstFocus(et_confirm, "两次密码不一致", Color.RED, true);
            return;
        }
        dialog=ProgressDialog.show(this,"","修改中,请稍后...",true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = mydbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                String password_origin=et_orgin.getText().toString();
                String password_new=et_new.getText().toString();
                values.put("password",password_new);
                db.update("userData", values, "name = ?", new String[] { password_origin });
                Toast.makeText(passwordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        },3000);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        /**
         * 当三个EditText的内容都不为空的时候，
         * Button为蓝色，否则为灰色通过
         * bt_confirm.setSelected(true)实现蓝色，
         *  bt_confirm.setSelected(false);实现灰色
         */
        if(!TextUtils.isEmpty(et_confirm.getText().toString())&&!TextUtils.isEmpty(et_orgin.getText().toString())
                &&!TextUtils.isEmpty(et_new.getText().toString())){
            bt_confirm.setSelected(true);
        }else{
            bt_confirm.setSelected(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    public void requstFocus(EditText et,String hint,int hintColor,boolean needFocus){
        if(hint==null){
            hint="请输入密码";
        }
        et.setHint(hint);
        et.setHintTextColor(hintColor);
        if(needFocus){
            et.requestFocus();
        }
    }

    private boolean checkNull() {
        if(TextUtils.isEmpty(et_orgin.getText().toString())){
            requstFocus(et_orgin, null, Color.GRAY,true);
            return true;
        }

        if(TextUtils.isEmpty(et_new.getText().toString())){
            requstFocus(et_new, null, Color.GRAY,true);
            return true;
        }

        if(TextUtils.isEmpty(et_confirm.getText().toString())){
            requstFocus(et_confirm,null, Color.GRAY,true);
            return true;
        }
        return false;
    }
    public void clearAll(){
        requstFocus(et_orgin, null, Color.GRAY,false);
        requstFocus(et_new, null, Color.GRAY,false);
        requstFocus(et_confirm,null, Color.GRAY,false);
    }
}
