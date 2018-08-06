package com.example.test.programmingmama.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.test.programmingmama.MainActivity;
import com.example.test.programmingmama.R;

public class Certificate extends AppCompatActivity {

    Toolbar toolbar;
    ImageView certificateimg;
    Context cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_certificate);

        cn=this;
        toolbar= findViewById(R.id.crt_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        certificateimg = findViewById(R.id.certificateimg);
        Glide.with(this).load(R.drawable.phcertificate).into(certificateimg);
        findViewById(R.id.clearning).setOnClickListener(v->{
            startActivity(new Intent(cn, MainActivity.class));
            finishAffinity();
        });
    }

    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        startActivity(new Intent(cn, MainActivity.class));
        finishAffinity();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
