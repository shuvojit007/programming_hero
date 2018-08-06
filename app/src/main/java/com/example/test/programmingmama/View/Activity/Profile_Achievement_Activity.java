package com.example.test.programmingmama.View.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.Adapter.SuperheroAdaptar;
import com.example.test.programmingmama.Utils.ItemDecoration;
import com.example.test.programmingmama.Utils.Service.RealmService;

import io.realm.Realm;

public class Profile_Achievement_Activity extends AppCompatActivity {


    RecyclerView achievementrec;
    Context cn;
    Realm realm;

    Toolbar toolbar;

    TextView superherocount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile__achievement);
        cn = this;
        init();
    }

    private void init() {

        achievementrec = findViewById(R.id.achievementrec);
        GridLayoutManager glm = new GridLayoutManager(cn, 3);
        achievementrec.setLayoutManager(glm);
        achievementrec.setItemAnimator(new DefaultItemAnimator());
        achievementrec.addItemDecoration(new ItemDecoration(0, 1, 0, 1));
        achievementrec.setAdapter(new SuperheroAdaptar(cn, RealmService.getAchievements()));


        toolbar= findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        superherocount = findViewById(R.id.superherocount);
        superherocount.setText(RealmService.getActiveAchievements().size()+"/"+ RealmService.getAchievements().size());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}