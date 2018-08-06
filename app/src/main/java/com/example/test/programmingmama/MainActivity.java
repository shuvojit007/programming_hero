package com.example.test.programmingmama;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.test.programmingmama.Utils.BottomNavigationBehavior;
import com.example.test.programmingmama.Utils.BottomNavigationViewHelper;
import com.example.test.programmingmama.Utils.PrefManager;
import com.example.test.programmingmama.Utils.Service.MyJobService;
import com.example.test.programmingmama.Utils.Service.RealmService;
import com.example.test.programmingmama.View.Fragment.Community;
import com.example.test.programmingmama.View.Fragment.ForumFragment;
import com.example.test.programmingmama.View.Fragment.More_Fragment;
import com.example.test.programmingmama.View.Fragment.Profile;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
//import com.stepstone.apprating.AppRatingDialog;
//import com.stepstone.apprating.listener.RatingDialogListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;
import static java.text.DateFormat.getDateInstance;

public class MainActivity extends AppCompatActivity {
    static BottomNavigationView navigation;
    Context cn;
    int index = 0;
    static Toolbar toolbar;
    String alert;
    String intentKey = "moduleName";
    TextView tl_mark;
    ImageView sound;
    ImageView tl_trophy;
    PrefManager prefManager;
    FirebaseJobDispatcher dispatcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        cn = this;
        prefManager = new PrefManager(cn);
        init();
    }


    @Override
    protected void onStop() {
        super.onStop();
        dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(cn));

        SimpleDateFormat spimdata = (SimpleDateFormat) getDateInstance();
        String time = spimdata.format(Calendar.getInstance().getTime());

        prefManager.setTime(time);
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class) // the JobService that will be called
                .setTag("my-unique-tag")
                .setRecurring(true)
                // don't persist past a device reboot
                .setLifetime(Lifetime.FOREVER)
                // start between 0 and 60 seconds from now
                .setTrigger(Trigger.executionWindow(0, 60))
                // don't overwrite an existing job with the same tag
                .setReplaceCurrent(true)
                // retry with exponential backoff
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                // constraints that need to be satisfied for the job to run
                .setConstraints(
//                        Constraint.DEVICE_CHARGING,
//                        Constraint.ON_UNMETERED_NETWORK,
//                        Constraint.DEVICE_CHARGING,
                        Constraint.ON_ANY_NETWORK
                )

                // uniquely identifies the job
                .build();
        // dispatcher.mustSchedule(myJob);
    }

    private void init() {
        toolbar = findViewById(R.id.main_app_bar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        tl_trophy = findViewById(R.id.tl_trophy);
    //    Glide.with(cn).load(R.drawable.tophyanim).into(m);
        tl_mark = findViewById(R.id.tl_mark);
        tl_mark.setText(String.valueOf(RealmService.TotalMark()));
        sound = findViewById(R.id.sound);

        sound.setOnClickListener(vv -> {
            if (prefManager.SoundStatus()) {
                prefManager.SoundOnOFF(false);
                sound.setImageResource(R.drawable.pause);
            } else {
                prefManager.SoundOnOFF(true);
                sound.setImageResource(R.drawable.play);
            }

        });

        setSupportActionBar(toolbar);
        navigation = findViewById(R.id.navigation);
        BottomNavigationViewHelper.removeShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        loadFragment(new Community());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //Todo =============Onlick Bottom Navigation==================
    private BottomNavigationView.
            OnNavigationItemSelectedListener
            mOnNavigationItemSelectedListener = (item) -> {
        switch (item.getItemId()) {
            case R.id.home:
                if (index != 0) {
                    loadFragment(new Community());
                    index = 0;
                }
                return true;
            case R.id.community:
                if (index != 2) {
                    loadFragment(new ForumFragment());
                    index = 2;
                }
                return true;
            case R.id.profile:
                if (index != 3) {
                    loadFragment(new Profile());
                    index = 3;
                }
                return true;
            case R.id.bug:
                if (index != 4) {
                    loadFragment(new More_Fragment());
                    index = 4;
                }
                return true;
        }
        return false;
    };


    //todo =============Load Fragment in FrameLayout========//
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        if (index != 0) {
            loadFragment(new Community());
            index = 0;
            navigation.setSelectedItemId(0);
            navigation.setVisibility(View.VISIBLE);
        } else {
            finishAffinity();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (prefManager.SoundStatus()) {
            sound.setImageResource(R.drawable.play);
        } else {
            sound.setImageResource(R.drawable.pause);
        }

    }

    public static void Quick(int i) {
        if (i == 0) {
            navigation.setVisibility(View.VISIBLE);
        } else {
            navigation.setVisibility(View.GONE);
        }
    }
}
