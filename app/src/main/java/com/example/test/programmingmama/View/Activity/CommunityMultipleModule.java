package com.example.test.programmingmama.View.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;


import com.example.test.programmingmama.Model.NewModel.Home;
import com.example.test.programmingmama.Model.NewModel.List;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.Adapter.CommunityMultipleModuleRec;
import com.example.test.programmingmama.Utils.ItemDecoration;
import com.example.test.programmingmama.Utils.Service.RealmService;

import io.realm.Realm;

public class CommunityMultipleModule extends AppCompatActivity {
    RecyclerView mRec;
    java.util.List<List> lm;
    Home hm;
    Context cn;
    TextView module_name,totalpoint,totalcard;
    int id;
    Realm realm;
    Toolbar tl;
    CommunityMultipleModuleRec adp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_multiple_module);
        cn =this;

        realm =Realm.getDefaultInstance();
        hm =getIntent().getParcelableExtra("parcel");
        id = hm.getId();

        init(cn);
    }

    @Override
    protected void onStart() {
        super.onStart();

        lm = RealmService.GetListById(realm,id);
        adp =new CommunityMultipleModuleRec(cn,lm,id);
        totalpoint.setText("Total Points "+hm.getTotal());
        getSupportActionBar().setTitle("Programming Hero");
        totalcard.setText("Total Cards "+lm.size());
        module_name.setText(hm.getTitle());
        mRec.setAdapter(adp);
        adp.notifyDataSetChanged();


    }

    private void init(Context cn) {
        tl =findViewById(R.id.submoduletl);
        setSupportActionBar(tl);

        mRec = findViewById(R.id.commultiplerec);
        module_name =findViewById(R.id.module_name);
        totalpoint =findViewById(R.id.totalpoint);
        totalcard =findViewById(R.id.totalcard);

//        GridLayoutManager glm = new GridLayoutManager(cn, 2);
//        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                if (position % 3 == 2) {
//                    return 2;
//                } else {
//                    return 1;
//                }
//            }
//        });
        mRec.setLayoutManager(new LinearLayoutManager(this));
       // mRec.setLayoutManager(glm);
        mRec.setItemAnimator(new DefaultItemAnimator());
        mRec.addItemDecoration(new ItemDecoration(10, 0, 10, 0));

    }
}
