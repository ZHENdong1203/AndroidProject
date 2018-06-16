package com.example.myapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MonitorActivity extends Activity {

    private Toast mToast;
    private Button login;
    private Button forget;
    private MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDBHelper(this,"UserData.db",null,1);

        final ClearEditText username = (ClearEditText) findViewById(R.id.username);
        final ClearEditText password = (ClearEditText) findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        forget=(Button)findViewById(R.id.forget);

        buttonListener listener=new buttonListener();
        forget.setOnClickListener(listener);

        register("0902160401","0902160401","刘琛");
        register("0902160402","0902160402","李俏岩");
        register("0902160404","0902160404","利育桦");
        register("0902160405","0902160405","高永儒");
        register("0902160406","0902160406","李凯琛");
        register("0902160407","0902160407","宋浩然");
        register("0902160408","0902160408","杨振东");
        register("0902160410","0902160410","黄沧海");
        register("0902160411","0902160411","李亮");
        register("0902160413","0902160413","尹傲雄");
        register("0902160414","0902160414","张瑞");
        register("0902160415","0902160415","常振乾");
        register("0902160416","0902160416","邓浩文");
        register("0902160417","0902160417","李帅帅");
        register("0902160419","0902160419","汪光虎");
        register("0902160420","0902160420","杨文卓");
        register("0902160421","0902160421","周高强");
        register("0902160422","0902160422","罗艺");
        register("0902160423","0902160423","杨泽元");
        register("0902160424","0902160424","郑楠");
        register("0902160425","0902160425","邓佩");
        register("0902160426","0902160426","张文志");
        register("0902160428","0902160428","廖潘彤");
        register("0902160429","0902160429","神孟秋");
        register("0801160408","0801160408","李宇轩");
        register("1203160126","1203160126","王汉臣");




        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(username.getText())){
                    username.setShakeAnimation();
                    showToast("用户名不能为空");
                    return;
                }

                if(TextUtils.isEmpty(password.getText())){
                    password.setShakeAnimation();
                    showToast("密码不能为空");
                    return;
                }


                if(login(username.getText().toString(),password.getText().toString())) {
                    Intent intent=new Intent();
                    String strID=username.getText().toString();
                    intent.setClass(MonitorActivity.this,MainActivity.class);
                    intent.putExtra("id",strID);
                    startActivity(intent);
                }else{
                    showWrongDialog();
                }
            }

        });

    }

    public boolean login(String username,String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from userData where name=? and password=?";
        Cursor cursor = db.rawQuery(sql, new String[]{username, password});
        if (cursor.moveToNext()) {
            cursor.close();
            return true;
        }
        return false;
    }


    public void register(String username,String password,String name){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",username);
        values.put("password",password);
        values.put("username",name);
        db.insert("userData",null,values);
        db.close();
    }

    private void update(){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
//修改SQL语句
        String sql = "update userData set password = name ";

//执行SQL
        db.execSQL(sql);
    }



    public void showToast(String msg){
        if(mToast == null){
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(msg);
        }
        mToast.show();

    }

    class buttonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            showbuttonForgetDialog();
        }
    }

    private void showbuttonForgetDialog(){
        new AlertDialog.Builder(this)
                .setMessage("默认密码:与学号相同")
                .setPositiveButton("恢复默认密码",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            update();
                    }
                })
                .setNegativeButton("返回",null)
                .show();
    }

    private void showWrongDialog(){
        new AlertDialog.Builder(this)
                .setMessage("用户名或密码错误")
                .setPositiveButton("好的",null)
                .show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if (keyCode==KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder isExit=new AlertDialog.Builder(this);
            isExit.setTitle("提醒");
            isExit.setMessage("退出");
            isExit.setPositiveButton("确定",diaListener);
            isExit.setNegativeButton("取消",diaListener);
            isExit.show();
        }
        return false;
    }

    DialogInterface.OnClickListener diaListener=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int buttonId) {
            switch (buttonId) {
                case AlertDialog.BUTTON_POSITIVE:
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:
                    break;
                default:
                    break;
            }
        }
    };
}
