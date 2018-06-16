package com.example.myapp;



import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;




public class MainActivity extends Activity implements View.OnClickListener{

    private TextView topBar;
    private TextView tabDeal;
    private TextView tabPoi;
    private TextView tabUser;
    private Button roundButton;

    private FrameLayout ly_content;

    private FirstFragment f1;
    private SecondFragment f2;
    private ThirdFragment f3;
    private SliderLayout mDemoSlider;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);



        bindView();

    }

    //UI组件初始化与事件绑定
    private void bindView() {
        topBar = (TextView)this.findViewById(R.id.txt_top);
        tabDeal = (TextView)this.findViewById(R.id.txt_deal);
        tabPoi = (TextView)this.findViewById(R.id.txt_poi);
        tabUser = (TextView)this.findViewById(R.id.txt_user);
        roundButton=(Button)this.findViewById(R.id.roundButton);
        ly_content = (FrameLayout) findViewById(R.id.fragment_container);
        tabDeal.setSelected(true);
        topBar.setText("签到");

        tabDeal.setOnClickListener(this);
        tabUser.setOnClickListener(this);
        tabPoi.setOnClickListener(this);




        rbuttonListener rlistener=new rbuttonListener();
        roundButton.setOnClickListener(rlistener);
        slider();







    }

    private void slider(){
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        TextSliderView textSliderView1 = new TextSliderView(this);
        textSliderView1
                .description("中南大学")//描述
                .image(R.drawable.first)//image方法可以传入图片url、资源id、File
                .setScaleType(BaseSliderView.ScaleType.Fit)//图片缩放类型
                .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        Uri uri = Uri.parse("http://www.csu.edu.cn/");
                        Intent fintent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(fintent);
                    }
                });//图片点击
        textSliderView1.bundle(new Bundle());
        textSliderView1.getBundle().putString("extra", "中南大学");//传入参数

        TextSliderView textSliderView2 = new TextSliderView(this);
        textSliderView2
                .description("教务系统")//描述
                .image(R.drawable.second)//image方法可以传入图片url、资源id、File
                .setScaleType(BaseSliderView.ScaleType.Fit)//图片缩放类型
                .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        Uri uri = Uri.parse("http://csujwc.its.csu.edu.cn/");
                        Intent seintent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(seintent);
                    }
                });//图片点击
        textSliderView2.bundle(new Bundle());
        textSliderView2.getBundle().putString("extra", "教务系统");//传入参数

        TextSliderView textSliderView3 = new TextSliderView(this);
        textSliderView3
                .description("信息门户")//描述
                .image(R.drawable.third)//image方法可以传入图片url、资源id、File
                .setScaleType(BaseSliderView.ScaleType.Fit)//图片缩放类型
                .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        Uri uri = Uri.parse("http://my.its.csu.edu.cn/");
                        Intent tintent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(tintent);
                    }
                });//图片点击
        textSliderView3.bundle(new Bundle());
        textSliderView3.getBundle().putString("extra", "信息门户");//传入参数

        mDemoSlider.addSlider(textSliderView1);//添加一个滑动页面
        mDemoSlider.addSlider(textSliderView2);//添加一个滑动页面
        mDemoSlider.addSlider(textSliderView3);//添加一个滑动页面
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);//滑动动画
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());//设置图片描述显示动画
        mDemoSlider.setDuration(4000);//设置滚动时间，也是计时器时间
        mDemoSlider.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
                                                @Override
                                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                                }

                                                @Override
                                                public void onPageSelected(int position) {
                                                    Log.d("ansen", "Page Changed: " + position);
                                                }

                                                @Override
                                                public void onPageScrollStateChanged(int state) {
                                                }
                                            }
        );
    }



    class rbuttonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this,SetupActivity.class);
            startActivity(intent);
        }
    }

    //重置所有文本的选中状态
    public void selected(){
        tabDeal.setSelected(false);
        tabPoi.setSelected(false);
        tabUser.setSelected(false);
    }

    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction){
        if(f1!=null){
            transaction.hide(f1);
        }
        if(f2!=null){
            transaction.hide(f2);
        }
        if(f3!=null){
            transaction.hide(f3);
        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch(v.getId()){
            case R.id.txt_deal:
                selected();
                topBar.setText("签到");
                tabDeal.setSelected(true);
                roundButton.setVisibility(View.VISIBLE);
                mDemoSlider.setVisibility(View.VISIBLE);
                if(f1==null){
                    f1 = new FirstFragment();
                    transaction.add(R.id.fragment_container,f1);
                }else{
                    transaction.show(f1);
                }
                break;


            case R.id.txt_poi:
                selected();
                tabPoi.setSelected(true);
                topBar.setText("记事本");
                roundButton.setVisibility(View.INVISIBLE);
                mDemoSlider.setVisibility(View.INVISIBLE);
                if(f2==null){
                    f2 = new SecondFragment();
                    transaction.add(R.id.fragment_container,f2);
                }else{
                    transaction.show(f2);
                }
                break;

            case R.id.txt_user:
                selected();
                tabUser.setSelected(true);
                topBar.setText("我");
                roundButton.setVisibility(View.INVISIBLE);
                mDemoSlider.setVisibility(View.INVISIBLE);
                if(f3==null){
                    Intent intentID=getIntent();
                    String id=intentID.getStringExtra("id");
                    Bundle bundle = new Bundle();
                    f3 = new ThirdFragment();
                    bundle.putString("str", id);
                    f3.setArguments(bundle);
                    transaction.add(R.id.fragment_container,f3);
                }else{
                    transaction.show(f3);
                }
                break;
        }

        transaction.commit();
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
