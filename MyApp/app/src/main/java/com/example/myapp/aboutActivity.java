package com.example.myapp;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class aboutActivity extends Activity {
    private Button about_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.about_layout);
        about_back=(Button)findViewById(R.id.about_back_btn);

        backListener listener=new backListener();
        about_back.setOnClickListener(listener);

    }

    class backListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            finish();
        }
    }
}