package com.example.test.programmingmama.View.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.test.programmingmama.App;
import com.example.test.programmingmama.Di.Community_Fragment_feature.CommunityFragmentComponent;
import com.example.test.programmingmama.Di.Community_Fragment_feature.DaggerCommunityFragmentComponent;
import com.example.test.programmingmama.MainActivity;
import com.example.test.programmingmama.Model.Achievement;
import com.example.test.programmingmama.Model.NewModel.Home;
import com.example.test.programmingmama.Model.NewModel.NewHome;
import com.example.test.programmingmama.Networking.Community_Api;
import com.example.test.programmingmama.Networking.SuperHero_Api;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.Adapter.CommunityAdapter;
import com.example.test.programmingmama.Utils.ItemDecoration;
import com.example.test.programmingmama.Utils.PrefManager;
import com.example.test.programmingmama.Utils.Service.RealmService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class Community extends Fragment {
    Context cn;
    List<Home> home = new ArrayList<>();
    List<Achievement> achievement  = new ArrayList<>();


    ProgressBar progress;
    @Inject
    Community_Api api;


    @Inject
    SuperHero_Api superHero_api;
    @Inject
    Picasso picasso;

    ProgressDialog pd;

    CommunityAdapter adp;
    RecyclerView comrec;
    View v;
    Realm realm;

    int homePos;
    PrefManager pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.community_fragment, container, false);
        cn = getContext();

        pref =new PrefManager(cn);
        homePos =pref.GetHomePosition();
        realm = Realm.getDefaultInstance();
        setUpDagger2(cn);
        init(cn);
//        DoNetWorkOperation();
        return v;
    }

    private void init(Context cn) {

        progress = v.findViewById(R.id.progress);

        comrec = v.findViewById(R.id.communityrec);


        GridLayoutManager glm = new GridLayoutManager(getActivity(), 2);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position % 3 == 2) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        comrec.setLayoutManager(new LinearLayoutManager(cn));
        comrec.setItemAnimator(new DefaultItemAnimator());
        comrec.addItemDecoration(new ItemDecoration(0, 1, 0, 1));
    //    comrec.smoothScrollToPosition(pref.GetHomePosition());



    }



    @Override
    public void onPause() {
        super.onPause();


    }

    //todo==========Set Up Dagger 2 =================//
    public void setUpDagger2(Context cn) {

        CommunityFragmentComponent communityFragmentComponent =
                DaggerCommunityFragmentComponent
                        .builder()
                        .appComponent(App.get((Activity) cn).getAppComponent())
                        .build();

        communityFragmentComponent.injectCommunity(Community.this);

    }
    //todo==========Set Up Dagger 2 =================//


    private void DoNetWorkOperation() {

        home =RealmService.getRealmHome(realm);
        achievement =RealmService.getAchievements();
        if(home.size()==0){
            progress.setVisibility(View.VISIBLE);

            api.GetCommunitySection()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

                    .subscribe(events -> {
                        ToastMsg(events);
                    }, error -> {
                       // pd.dismiss();
                        Log.e("ResEvent Error", error.getMessage());
                        Toast.makeText(cn, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    });
        }else {
            adp = new CommunityAdapter(cn,home,picasso,pref);
            comrec.setAdapter(adp);
        }

        if(achievement.size()==0){

            superHero_api.getAcievementSuperHeroList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(events -> {
                       // pd.dismiss();
                        for (Achievement md : events) {
                            RealmService.StoreAchievement(md);
                        }

                    }, error -> {
                        //pd.dismiss();
                        Log.e("ResEvent Error", error.getMessage());
                        Toast.makeText(cn, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    });

        }

        if(pd!=null){
            pd.dismiss();
        }


    }

    private void ToastMsg(List<NewHome> events) {

        progress.setVisibility(View.GONE);
    //    pd.dismiss();
        for (NewHome md : events) {
            RealmService.StoreJson(md,realm);

        }
        if(home!=null){
         //   home.clear();
            home =realm.copyFromRealm((RealmService.getRealmHome(realm)));
        }else {
            home =(RealmService.getRealmHome(realm));
        }



        if(home.size()==0){
            DoNetWorkOperation();
        }else {


            adp = new CommunityAdapter(cn,home,picasso,pref);
            comrec.setAdapter(adp);
        }

    }


    @Override
    public void onStart() {
        super.onStart();


        DoNetWorkOperation();

    }
}



