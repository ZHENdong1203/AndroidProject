package com.example.myapp;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


@SuppressLint("ValidFragment")
public class ThirdFragment extends Fragment {
    private ImageView blurImageView;
    private ImageView avatarImageView;
    private item_view about;
    private item_view password;
    private item_view logout;
    private TextView id;
    private TextView naMe;
    private MyDBHelper dbHelper;
    private String name;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third_fragment,container,false);
        blurImageView = (ImageView) view.findViewById(R.id.iv_blur);
        avatarImageView = (ImageView) view.findViewById(R.id.iv_avatar);
        about=(item_view)view.findViewById(R.id.about);
        password=(item_view)view.findViewById(R.id.change_password);
        logout=(item_view)view.findViewById(R.id.logout);
        id=(TextView)view.findViewById(R.id.user_val);
        naMe=(TextView)view.findViewById(R.id.user_name);
        dbHelper = new MyDBHelper(this.getActivity(),"UserData.db",null,1);


        Glide.with(this).load(R.drawable.head_background)
                .bitmapTransform(new BlurTransformation(this.getActivity(), 25), new CenterCrop(this.getActivity()))
                .into(blurImageView);

        Glide.with(this).load(R.drawable.head_background)
                .bitmapTransform(new CropCircleTransformation(this.getActivity()))
                .into(avatarImageView);

        aboutListener listener=new aboutListener();
        about.setOnClickListener(listener);
        passwordListener listener1=new passwordListener();
        password.setOnClickListener(listener1);
        logoutListener listener2=new logoutListener();
        logout.setOnClickListener(listener2);

        Bundle bundle = getArguments();//从activity传过来的Bundle

        final String studentName=bundle.getString("str");

        id.setText(studentName);
        String strName=id.getText().toString();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from userData where name=? ";
        Cursor cursor1 = db.rawQuery(sql, new String[]{strName});
        while(cursor1.moveToNext()){
            name=cursor1.getString(cursor1.getColumnIndex("username"));
        }
        naMe.setText(name);
        id.setVisibility(View.VISIBLE);
        return view;


    }

    class aboutListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent aboutIntent=new Intent();
            aboutIntent.setClass(getActivity(),aboutActivity.class);
            startActivity(aboutIntent);
        }
    }

    class passwordListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent passwordIntent=new Intent();
            passwordIntent.setClass(getActivity(),passwordActivity.class);
            startActivity(passwordIntent);
        }
    }
    class logoutListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent logoutIntent=new Intent();
            logoutIntent.setClass(getActivity(),MonitorActivity.class);
            startActivity(logoutIntent);
            getActivity().finish();
        }
    }

}