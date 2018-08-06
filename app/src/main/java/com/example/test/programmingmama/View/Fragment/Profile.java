package com.example.test.programmingmama.View.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.test.programmingmama.MainActivity;
import com.example.test.programmingmama.Model.Achievement;
import com.example.test.programmingmama.Model.NewModel.Home;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.GetFirebaseRef;
import com.example.test.programmingmama.Utils.PrefManager;
import com.example.test.programmingmama.Utils.Service.RealmService;
import com.example.test.programmingmama.View.Activity.Profile_Achievement_Activity;
import com.example.test.programmingmama.View.Activity.Profile_ModuleList_Activity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hadiidbouk.charts.BarData;
import com.hadiidbouk.charts.ChartProgressBar;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;


public class Profile extends Fragment {
    View v;
    Context cn;

    ScrollView profilescroll;

    TextView pro_module_count,pro_achivement_count;

    TextView crmodule,nxtmodule;

    TextView pro_module_totalpoint,profilecurrentbadge,pro_module_nextbadge,profilename;

    CircleImageView profilebadge;

    ImageView profile_trophy;
    Realm realm;
    Achievement achievement;
    PrefManager pref;


    private ChartProgressBar mChart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.profile_fragment02, container, false);
        cn = getContext();
        init();


        return v;
    }

    private void init() {

        crmodule =v.findViewById(R.id.crmodule);
        nxtmodule =v.findViewById(R.id.nxtmodule);
        Home hm =RealmService.FindModuleLast();
        crmodule.setText("Current : "+hm.getTitle());
        nxtmodule.setText("Next : "+RealmService.FindLast(hm.getId()));

        pref =new PrefManager(cn);
        profilename = v.findViewById(R.id.profilename);
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
             GetFirebaseRef.GetDbIns()
                    .getReference()
                    .child("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                     .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        String name = dataSnapshot.child("name").getValue().toString();
                        profilename.setText(name);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        profile_trophy = v.findViewById(R.id.profile_trophy);
        Glide.with(cn).load(R.drawable.tophyanim).into(profile_trophy);

        realm  =Realm.getDefaultInstance();
        profilebadge = v.findViewById(R.id.profilebadge);
        profilecurrentbadge = v.findViewById(R.id.profilecurrentbadge);
        pro_module_nextbadge =v.findViewById(R.id.pro_module_nextbadge);
        pro_module_totalpoint =v.findViewById(R.id.pro_module_totalpoint);
        pro_module_totalpoint.setText("Total Point "+RealmService.TotalMark()+"XP");

        pro_achivement_count = v.findViewById(R.id.pro_achivement_count);
        pro_module_count = v.findViewById(R.id.pro_module_count);
        pro_achivement_count.setText(RealmService.TotalCompleteAchivementModule(realm)+"/"+(RealmService.TotalAchivementModule(realm)-1));
        pro_module_count.setText(RealmService.TotalCompletedModule(realm)+"/"+(RealmService.TotaldModule(realm)-1));

        achievement =RealmService.GetLatestAchievement();
       if( achievement==null){
           profilecurrentbadge.setVisibility(View.GONE);
       }else {

           profilecurrentbadge.setText(achievement.getName());
           Glide.with(cn)
                   .load(achievement.getIcon())
                   .into(profilebadge);


       }


        if(achievement!=null){
            if(RealmService.GetNExtAchievement(achievement.getId()+1)!=null){
                pro_module_nextbadge.setText("NEXT BADGE "+ RealmService
                        .GetNExtAchievement(achievement.getId()+1)
                        .getName());
            }
        }


        v.findViewById(R.id.profilemodule)
                .setOnClickListener(vv ->
                        startActivity(new Intent(cn,
                                Profile_ModuleList_Activity.class)));


        v.findViewById(R.id.profileachievements)
                .setOnClickListener(vv ->
                        startActivity(new Intent(cn,
                                Profile_Achievement_Activity.class)));
//
        profilescroll = v.findViewById(R.id.profilescroll);
        profilescroll.getViewTreeObserver().addOnScrollChangedListener(() -> {
            int scrollY = profilescroll.getScrollY(); // For ScrollView
            if (scrollY > 0) {
                MainActivity.Quick(1);
            } else {
                MainActivity.Quick(0);
            }
        });




        ArrayList<BarData> dataList = new ArrayList<>();
        BarData data = new BarData("First Times", pref.GetFIRST(), pref.GetFIRST()+"");
        dataList.add(data);
        data = new BarData("Second Times", pref.GetSECEND(), pref.GetSECEND()+"");
        dataList.add(data);
        data = new BarData("GO Back", pref.GetGoBack(), pref.GetGoBack()+"");
        dataList.add(data);

        mChart = v.findViewById(R.id.ChartProgressBar);
        mChart.setDataList(dataList);
        mChart.build();
        //  mChart.setOnBarClickedListener(this);
      //  mChart.disableBar(dataList.size() - 1);
    }


}
