package com.example.test.programmingmama.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.test.programmingmama.MainActivity;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.PrefManager;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

public class Splash_Screen extends AppCompatActivity {
    Context cn;
    private static final int SPLASH_DELAY = 2000;
    Handler handler;
    ImageView spLogo;
    TextView name;
    Animation anim;

    private PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
        if (prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finishAffinity();
        }else {

            setContentView(R.layout.activity_splash__screen);
            cn = this;
            anim = AnimationUtils.loadAnimation(this, R.anim.up);
            Animation fadein = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            spLogo = findViewById(R.id.sp_logo);

            Glide.with(cn).load(R.drawable.superhero).apply(fitCenterTransform()).into(spLogo);
            name = findViewById(R.id.sp_name);
            name.setAnimation(fadein);

            handler = new Handler();

            handler.postDelayed(() -> {
                startActivity(new Intent(Splash_Screen.this, MainActivity.class));
                finish();
            }, SPLASH_DELAY);
        }







    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(Splash_Screen.this, WelcomeActivity.class)
                .putExtra("welcome",0));

    }


}
