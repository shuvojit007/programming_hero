package com.example.test.programmingmama.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.test.programmingmama.MainActivity;
import com.example.test.programmingmama.Model.Achievement;
import com.example.test.programmingmama.Model.Challenge2.Challenge2;
import com.example.test.programmingmama.Model.Challenge2.Challenge2Fields;
import com.example.test.programmingmama.Model.Challenge2.Pchallenge;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.Adapter.ChallengeAdapter;
import com.example.test.programmingmama.Utils.Service.RealmService;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

public class Challenge extends AppCompatActivity {
    RecyclerView rec;
    Context cn;
    Realm realm;
    Toolbar toolbar;
    Challenge2 challenge2List;
    ChallengeAdapter adp;

    public static boolean pcstatus = false;


    int TotalSize=0;
    public static  int SelectedAnsCounter=0;

    public static int CorretAnswer = 0;
    public static int WrongAnswer = 0;
    int id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_challenge);
        cn = this;

        pcstatus = RealmService.GetChallengeMOdulestatus(9);;

        init();
        SetupRealm();
        if(!pcstatus){
            IndicationPopup();
        }
    }

    private void IndicationPopup() {
        Achievement achievement =RealmService.findSuperHero(5);
        AlertDialog.Builder builder = new AlertDialog.Builder(cn);
        final View dialogView = LayoutInflater.from(cn).inflate(R.layout.indication,null);
        TextView name =dialogView.findViewById(R.id.popuptitle);
        TextView indication = dialogView.findViewById(R.id.popupmsg);
        ImageView popupimg =dialogView.findViewById(R.id.profilebadge);
        String[]indicate =achievement.getIndication().split("/");
        name.setText(indicate[0]);
        indication.setText(indicate[1]);

        Glide.with(cn).load(achievement.getIcon())
                .apply(fitCenterTransform())
                .into(popupimg);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void SetupRealm() {
        challenge2List=null;
        challenge2List = RealmService.getChallenge2();
        if(challenge2List==null){
            challenge2List =  new Gson().fromJson(loadJSONFromAsset(),Challenge2.class);
            RealmService.StoreChallengeJson(challenge2List);
            SetupRealm();
        }else {
            RealmList<Pchallenge> pc =challenge2List.getPchallenge();
            adp =new ChallengeAdapter(cn,pc);
            rec.setAdapter(adp);
            TotalSize = pc.size();
        }
    }

    public static void CorrectAnswer() {
        CorretAnswer = CorretAnswer + 1;

    }

    public static void AnsCounter() {
        SelectedAnsCounter = SelectedAnsCounter + 1;

    }


    public static void WrongAnswer() {
        WrongAnswer = WrongAnswer + 1;

    }



    private void init() {
        toolbar = findViewById(R.id.challenge_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Challenge 2");

        rec = findViewById(R.id.challenge_rec);
        rec.setLayoutManager(new LinearLayoutManager(cn));

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is =cn.getAssets().open("pchlng2.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if (TotalSize != SelectedAnsCounter) {
                    Toast.makeText(cn, "Please Answer all question", Toast.LENGTH_SHORT).show();
                } else {
                        if(CorretAnswer>=TotalSize-2){
                            if(pcstatus){
                                startActivity(new Intent(cn, MainActivity.class));
                                finishAffinity();
                            }else {
                                RealmService.UpdateChallengeModule(9);
                                Achievement achievement = RealmService.findSuperHero(5);
                                startActivity(new Intent(cn, Congrats_Pop_Up.class)
                                        .putExtra("popup", achievement.getName() + "/" + achievement.getMsg())
                                        .putExtra("value", 3)
                                        .putExtra("pc", true)
                                        .putExtra("icon",achievement.getIcon()));
                                RealmService.ChlngMOdule(10);
                                RealmService.UnlockSuperHero(5);
                                finishAffinity();
                            }
                        } else {

                            AlertDialog.Builder builder = new AlertDialog.Builder(cn);
                            final View dialogView = getLayoutInflater().inflate(R.layout.pchallenge3, null);
                            builder.setView(dialogView);
                            final AlertDialog dialog = builder.create();

                            TextView yes = dialogView.findViewById(R.id.yes);
                            TextView no = dialogView.findViewById(R.id.no);

                            yes.setOnClickListener(vv->{
                                SetupRealm();
                                dialog.dismiss();
                            });
                            no.setOnClickListener(vv->{
                                onBackPressed();
                            });
                            dialog.show();

                        }
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.progrrammingchallenge_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        startActivity(new Intent(cn, MainActivity.class));
        finishAffinity();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SelectedAnsCounter=0;
        CorretAnswer = 0;
        WrongAnswer = 0;
    }
}
