package com.example.test.programmingmama.View.ViewPager;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.badoualy.stepperindicator.StepperIndicator;
import com.example.test.programmingmama.Model.NewModel.Home;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.CustomViewPager;
import com.example.test.programmingmama.Utils.PrefManager;
import com.example.test.programmingmama.Utils.Service.RealmService;
import com.example.test.programmingmama.View.ViewPager.PagerAdapter.CommunityPagerAdapter;

public class CommunityViewPager extends AppCompatActivity {
    Context cn;
    private static ImageView btnleft,btnright;
    private  static Button btncenter;
    public static StepperIndicator stepperIndicator;
    public static CustomViewPager viewPager;
    public static int id;
    static TextView tl_mark;
    ImageView sound;
    PrefManager prefManager;

    int homePos;

    public static int badgeCounter =0;

    Toolbar tl;
    public static Home home ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_community_view_pager);
        id = getIntent().getIntExtra("count", 1);
        home =getIntent().getParcelableExtra("parcel");


        cn = this;
        init();
       if(id==1){
            BtnLeftVisible(0);
            BtnRightVisible(0);
        }

    }


//    public static void addPage(){
//        maxPageCount++; //or maxPageCount = fragmentPosition+2
//        mFragmentStatePagerAdapter.notifyDataSetChanged(); //notifyDataSetChanged is important here.
//    }


    private void init() {
        stepperIndicator = findViewById(R.id.communitystepper);
        viewPager = findViewById(R.id.communityviewpager);
        viewPager.setAdapter(new CommunityPagerAdapter(this, getSupportFragmentManager(), home.getCount(), home,viewPager));
        stepperIndicator.setViewPager(viewPager); //
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.left);
        btnleft = findViewById(R.id.btnleft);
        btnright = findViewById(R.id.btnright);
        btncenter = findViewById(R.id.btn);
        btncenter.setVisibility(View.VISIBLE);
        btncenter.setOnClickListener(vv->finish());
        btnright.setOnClickListener(vv-> RightForward());
        btnleft.setOnClickListener(vv-> BackForward());


        tl =findViewById(R.id.tl);
        tl.setTitleTextColor(Color.WHITE);
        setSupportActionBar(tl);
        prefManager =new PrefManager(cn);

        tl_mark = findViewById(R.id.tl_mark);
        tl_mark.setText(String.valueOf(RealmService.TotalMark()));
        sound =findViewById(R.id.sound);


        sound.setOnClickListener(vv->{
            if(prefManager.SoundStatus()){
                prefManager.SoundOnOFF(false);
                sound.setImageResource(R.drawable.pause);
            }else {
                prefManager.SoundOnOFF(true);
                sound.setImageResource(R.drawable.play);
            }

        });

    }

    public static void Updatetl(){
        tl_mark.setText(String.valueOf(RealmService.TotalMark()));
    }

    public static void CenterBtnVisible(int i){
        if(i==0){
            btncenter.setVisibility(View.INVISIBLE);
        }else {
            btncenter.setVisibility(View.VISIBLE);
        }
    }

    public static void BtnLeftVisible(int i){
        if(i==0){
            btnleft.setVisibility(View.GONE);
        }else {
            btnleft.setVisibility(View.VISIBLE);
        }
    }

    public static void BtnRightVisible(int i){
        if(i==0){
            btnright.setVisibility(View.GONE);
        }else {
            btnright.setVisibility(View.VISIBLE);
        }
    }

    public static boolean backstatus() {
        int i = getItem(-1);
        if (i == -1) {
            return false;
        } else {
            return true;
        }
    }

    public static void BackForward() {
        int i = getItem(-1);
        viewPager.setCurrentItem(i, true);
    }

    public static boolean Rightstatus() {
        int i = getItem(+1);
        if (id-1 < i) {
            btncenter.setVisibility(View.VISIBLE);
            return false;
        } else {
            return true;
        }
    }

    public static void RightForward() {
        int i = getItem(+1);
        viewPager.setCurrentItem(i, true);
    }


    public static void AddCounter(){
        badgeCounter = badgeCounter+1;
    }

    public static int getBCounter(){
        return  badgeCounter;
    }


    private static int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(prefManager.SoundStatus()){
            sound.setImageResource(R.drawable.play);

        }else {

            sound.setImageResource(R.drawable.pause);
        }

    }
}
