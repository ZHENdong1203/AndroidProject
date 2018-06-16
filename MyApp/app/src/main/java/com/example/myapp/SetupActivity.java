package com.example.myapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;
import com.yalantis.euclid.library.EuclidActivity;
import com.yalantis.euclid.library.EuclidListAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SetupActivity extends EuclidActivity{
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        back=(ImageView)findViewById(R.id.toolbar_profile_back1) ;
        imageListener listener=new imageListener();
        back.setOnClickListener(listener);

        mButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://classroom.csu.edu.cn/");
                Intent sintent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(sintent);
            }
        });
    }

    class imageListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            finish();
        }
    }

    @Override
    protected BaseAdapter getAdapter() {
        Map<String, Object> profileMap;
        List<Map<String, Object>> profilesList = new ArrayList<>();

        int[] avatars = {
                R.drawable.a,
                R.drawable.b,
                R.drawable.c,
                R.drawable.d,
                R.drawable.e,
                R.drawable.f,
                R.drawable.g,
                R.drawable.h,
                };
        String[] names = getResources().getStringArray(R.array.array_names);
        String[] lorem_ipsum_short=getResources().getStringArray(R.array.lorem_ipsum_short);
        String[] lorem_ipsum_long=getResources().getStringArray(R.array.lorem_ipsum_long);

        for (int i = 0; i < avatars.length; i++) {
            profileMap = new HashMap<>();
            profileMap.put(EuclidListAdapter.KEY_AVATAR, avatars[i]);
            profileMap.put(EuclidListAdapter.KEY_NAME, names[i]);
            profileMap.put(EuclidListAdapter.KEY_DESCRIPTION_SHORT, lorem_ipsum_short[i]);
            profileMap.put(EuclidListAdapter.KEY_DESCRIPTION_FULL, lorem_ipsum_long[i]);
            profilesList.add(profileMap);
        }

        return new EuclidListAdapter(this, R.layout.list_item, profilesList);
    }
}
