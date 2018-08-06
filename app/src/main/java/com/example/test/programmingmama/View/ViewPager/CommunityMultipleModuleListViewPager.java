package com.example.test.programmingmama.View.ViewPager;

import android.app.Activity;
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
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;

import com.example.test.programmingmama.Model.NewModel.List;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.CustomViewPager;
import com.example.test.programmingmama.Utils.PrefManager;
import com.example.test.programmingmama.Utils.Service.RealmService;
import com.example.test.programmingmama.View.ViewPager.PagerAdapter.CommunityPagerListAdapter;

import static com.example.test.programmingmama.View.ViewPager.CommunityViewPager.badgeCounter;

public class CommunityMultipleModuleListViewPager extends AppCompatActivity {
    static Context cn;
    private static ImageView btnleft,btnright;
    private  static Button btncenter;
    public static StepperIndicator stepperIndicator;
    public static CustomViewPager viewPager;
    public static int count;
    public static int id;
    public static Activity activity = null;
    public static List lm;
    static TextView tl_mark;
    ImageView sound;
    PrefManager prefManager;
    Toolbar tl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_community_multiple_module_list_details);
        count = getIntent().getIntExtra("count", 1);
        lm =getIntent().getParcelableExtra("parcel");
        id =getIntent().getIntExtra("id",0);
        Toast.makeText(this, String.valueOf(count), Toast.LENGTH_SHORT).show();
        cn = this;
        init();

        if(count==1){
            BtnLeftVisible(0);
            BtnRightVisible(0);
        }
    }

    private void init() {
        stepperIndicator = findViewById(R.id.comliststepper);
        viewPager = findViewById(R.id.comlistviewpager);
        viewPager.setAdapter(new CommunityPagerListAdapter(this, getSupportFragmentManager(), lm.getMcount(), lm,id));
        stepperIndicator.setViewPager(viewPager); //
      //  viewPager.setPagingEnabled(false);

        viewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.left);


        btnleft = findViewById(R.id.btnleft);
        btnright = findViewById(R.id.btnright);
        btncenter = findViewById(R.id.btn);
        viewPager.setOffscreenPageLimit(1);
        btncenter.setVisibility(View.VISIBLE);
        btncenter.setOnClickListener(vv->finish());
        btnright.setOnClickListener(vv-> RightForward());
        btnleft.setOnClickListener(vv-> BackForward());

        tl =findViewById(R.id.tl);
        tl.setTitleTextColor(Color.WHITE);
        setSupportActionBar(tl);
        getSupportActionBar().setTitle(lm.getMtitle());
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

    public static void FinishActivity(){
        activity.finish();
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

        if (count-1 < i) {
           // btncenter.setVisibility(View.VISIBLE);
            return false;
        } else {
            return true;        }
    }

    public static void RightForward() {
        int i = getItem(+1);


        viewPager.setCurrentItem(i, true);
    }

    public static void CenterBtnVisible(int i){
        if(i==0){
            btncenter.setVisibility(View.INVISIBLE);
        }else {
            btncenter.setVisibility(View.VISIBLE);
        }
    }

    public static void Updatetl(){
        tl_mark.setText(String.valueOf(RealmService.TotalMark()));
    }


    private static int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    public static void AddCounter(){
        badgeCounter = badgeCounter+1;
    }

    public static int getBCounter(){
        return  badgeCounter;
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
