package com.example.test.programmingmama.View.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.Adapter.ProfileModuleListRecAdp;
import com.example.test.programmingmama.Utils.ItemDecoration;
import com.example.test.programmingmama.Utils.Service.RealmService;

import io.realm.Realm;

public class Profile_ModuleList_Activity extends AppCompatActivity {

    RecyclerView promdrec;
    Context cn;
    Realm realm ;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile__module_list_);
        cn = this;
        init();

    }

    private void init() {
        toolbar= findViewById(R.id.tl);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        realm =Realm.getDefaultInstance();

        promdrec = findViewById(R.id.promdrec);
        promdrec.setLayoutManager(new LinearLayoutManager(this));
        promdrec.setItemAnimator(new DefaultItemAnimator());
        promdrec.addItemDecoration(new ItemDecoration(2, 0, 2, 0));
        promdrec.setAdapter(new ProfileModuleListRecAdp(cn, RealmService.getHome(realm)));


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
