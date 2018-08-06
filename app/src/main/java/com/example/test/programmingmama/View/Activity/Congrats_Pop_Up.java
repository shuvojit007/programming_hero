package com.example.test.programmingmama.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.test.programmingmama.MainActivity;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.GetFirebaseRef;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

public class Congrats_Pop_Up extends AppCompatActivity {
    KonfettiView konfettiView;
    String popup;
    int i;
    TextView popuptitle, popupmsg, popupfunfact, popuptap;
    ImageView cimg1;
    CircleImageView cimg12;

    String icon = "";
    MediaPlayer mp;
    Context cn;
    boolean pc = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.congratspopup);

        popup = getIntent().getStringExtra("popup");
        i = getIntent().getIntExtra("value", 1);
        cn = this;


        if (i == 3) {
            icon = getIntent().getStringExtra("icon");
            pc = getIntent().getBooleanExtra("pc", false);
            init2();
        } else {
            init();
        }


    }

    private void init2() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user =mAuth.getCurrentUser();
        if(user!=null){
            DatabaseReference mDatabaseReference = GetFirebaseRef
                    .GetDbIns()
                    .getReference()
                    .child("Users")
                    .child(user.getUid());
            HashMap map = new HashMap();
                    map.put("badge",icon);
        mDatabaseReference.updateChildren(map);

        }

        konfettiView = findViewById(R.id.viewKonfetti);


        konfettiView.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(new Size(12, 5))
                .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                .stream(300, 5000L);

//        mp = MediaPlayer.create(this, R.raw.crowd);
//        mp.start();

        popuptitle = findViewById(R.id.popuptitle);
        popupmsg = findViewById(R.id.popupmsg);
        popupfunfact = findViewById(R.id.popupfunfact);
        popupfunfact.setVisibility(View.GONE);
        popuptap = findViewById(R.id.popuptap);
        cimg1 = findViewById(R.id.cimg1);
        cimg1.setVisibility(View.GONE);

        cimg12 = findViewById(R.id.cimg12);


        String[] parts = popup.split("/");
        popuptitle.setText(parts[0]);
        popupmsg.setText(parts[1]);
        cimg12.setVisibility(View.VISIBLE);

        Glide.with(cn).load(icon).apply(fitCenterTransform()).into(cimg12);


        popuptap.setOnClickListener(vv ->
        {
            if (pc) {
                startActivity(new Intent(cn, MainActivity.class));
                finishAffinity();
            } else {
                finish();
            }


        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mp != null) {
            mp.stop();
        }
    }

    private void init() {
        popuptitle = findViewById(R.id.popuptitle);
        popupmsg = findViewById(R.id.popupmsg);
        popupfunfact = findViewById(R.id.popupfunfact);
        popuptap = findViewById(R.id.popuptap);

        String[] parts = popup.split("/");
        popuptitle.setText(parts[0]);
        popupmsg.setText(parts[1]);
        if (parts[2].equals("null")) {
            popupfunfact.setVisibility(View.GONE);
        } else {
            popupfunfact.setText(parts[2]);
        }

        popuptap.setOnClickListener(vv -> {

            if (i == 0) {
                finish();
            } else {

                startActivity(new Intent(cn, MainActivity.class));
                finishAffinity();
            }

        });

    }


}
