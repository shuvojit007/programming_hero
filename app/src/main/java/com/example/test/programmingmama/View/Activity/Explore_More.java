package com.example.test.programmingmama.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.test.programmingmama.MainActivity;
import com.example.test.programmingmama.Model.Explore_Info;
import com.example.test.programmingmama.Model.Explore_Model;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.Explore_Adapter.RecAdapter;
import com.example.test.programmingmama.Utils.GetFirebaseRef;
import com.example.test.programmingmama.Utils.ItemDecoration;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Explore_More extends AppCompatActivity {
    Context cn;
    List<Explore_Model> pmodel;
    DatabaseReference db = GetFirebaseRef.GetDbIns().getReference().child("Explore");
    private RecyclerView mExpandableRec;
    ProgressBar progress;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_explore__more);
        cn = this;
        init();
    }

    private void init() {
        toolbar= findViewById(R.id.exp_tl);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Explore More");


        progress = findViewById(R.id.progress);
        mExpandableRec = findViewById(R.id.explore_rec);
        LinearLayoutManager lin = new LinearLayoutManager(cn);
        mExpandableRec.setLayoutManager(lin);
        mExpandableRec.setItemAnimator(new DefaultItemAnimator());
        mExpandableRec.addItemDecoration(new ItemDecoration(5, 5, 5, 0));


        pmodel = new ArrayList<>();
        progress.setVisibility(View.VISIBLE);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //pmodel.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        List<Explore_Info> cmodel = new ArrayList<>();
                        String category = ds.child("category").getValue().toString();
                        Log.d("ExData", "parent  -- >  " + category);
                        for (DataSnapshot ds3 : ds.child("data").getChildren()) {

                            String caption = ds3.child("caption").getValue().toString();
                            String image_url = ds3.child("image_url").getValue().toString();
                            String link = ds3.child("link").getValue().toString();
                            String title = ds3.child("title").getValue().toString();

                            cmodel.add(new Explore_Info(title, image_url, caption, link));
                            Log.d("ExData", "child  -- >  " + caption + "\n");
                        }

//                        Set hs = new HashSet<>();
//                        hs.addAll(cmodel);
//                        cmodel.clear();
//                        cmodel.addAll(hs);
                        pmodel.add(new Explore_Model(category, cmodel));

                    }

                    mExpandableRec.setAdapter(new RecAdapter(pmodel, cn));
                    progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //   Log.d(TAG, databaseError.getMessage());
            }
        });

        RecyclerView.ItemAnimator animator = mExpandableRec.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(cn, MainActivity.class));
        finishAffinity();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
