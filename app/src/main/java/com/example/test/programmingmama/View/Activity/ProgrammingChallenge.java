package com.example.test.programmingmama.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
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
import com.example.test.programmingmama.Model.pc1Model;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.Adapter.ProgrammingChallengeAdapter;
import com.example.test.programmingmama.Utils.Service.RealmService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;


public class ProgrammingChallenge extends AppCompatActivity {
    private static final String TAG = "MyProgrammingChallen";
    RecyclerView rec;
    Context cn;
    Realm realm;
    Toolbar toolbar;
    List<pc1Model> pc1Models;
    public static int CorretAnswer = 0;
    public static int WrongAnswer = 0;
    public static boolean status = false;
    public static boolean complete = false;


    public static boolean pcstatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_programming_challenge);
        cn = this;
        pcstatus=  RealmService.GetPcMOdulestatus(0);

        init();
        SetUpRealm();
        if(!pcstatus){
            IndicationPopup();
        }
    }


    private void IndicationPopup() {
        Achievement achievement =RealmService.findSuperHero(3);
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


    private void SetUpRealm() {
        realm = Realm.getDefaultInstance();
        pc1Models = RealmService.getPC1Model(realm);
        if (pc1Models.size() == 0) {
            Log.d(TAG, "SetUpRealm: pcmodelnull");

            pc1Models = new ArrayList<>();
            pc1Models.add(new pc1Model("How to show output from a program", "show()", "output()", "print()", "active", 3));
            pc1Models.add(new pc1Model("Which one is a string type variable ", "fruit = ‘Banana’", "weight = 121", "is_hot = False", "inactive", 1));
            pc1Models.add(new pc1Model("Which one is the correct way to declare a variable name ", "12_class_kids = 141", "_class_kids_12 = 141", "twelve-class-kid = 141", "inactive", 1));
            pc1Models.add(new pc1Model("What would be the output from the following code?\n" +
                    "\n" +
                    "security = ‘Tom Hanks’\n" +
                    "Security = ‘Tom Cruise’\n" +
                    "print(security)\n", "Tom Hanks", "Tom Cruise", "Nothing", "active", 1));
            pc1Models.add(new pc1Model("What does float() do", "Convert string to number", "Float the in the river", "show string as output", "inactive", 1));
            pc1Models.add(new pc1Model("Which one is the correct way to declare string type variable ", "season = summer", "season = ‘sprint”", "season = “winter”", "inactive", 3));

            RealmService.SaveProgrramingchallenge01(pc1Models, realm);
            pc1Models = RealmService.getPC1Model(realm);
            if (pc1Models == null) {
                Toast.makeText(cn, "pc1model null", Toast.LENGTH_SHORT).show();
            } else {

                ProgrammingChallengeAdapter adp = new ProgrammingChallengeAdapter(cn, pc1Models);
                rec.setAdapter(adp);
            }

        } else {

            ProgrammingChallengeAdapter adp = new ProgrammingChallengeAdapter(cn, pc1Models);
            rec.setAdapter(adp);
            ;

        }

    }


    public static void CorrectAnswer() {
        CorretAnswer = CorretAnswer + 1;

    }


    public static void WrongAnswer() {
        WrongAnswer = WrongAnswer + 1;

    }

    public static void setStatus() {
        status = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }


    private void init() {
        toolbar = findViewById(R.id.main_app_bar);
        setSupportActionBar(toolbar);
        rec = findViewById(R.id.quizrec);
        rec.setLayoutManager(new LinearLayoutManager(cn));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.progrrammingchallenge_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:


                if (status == false) {
                    Toast.makeText(cn, "Please Answer all question", Toast.LENGTH_SHORT).show();
                } else {
                    if(complete==false){
                        if(CorretAnswer>=4){
                            complete=true;
                            if(pcstatus){
                                startActivity(new Intent(cn, MainActivity.class));
                                finishAffinity();
                            }else {
                                RealmService.UpdatePcModule(0);

                                Achievement achievement = RealmService.findSuperHero(3);
                                startActivity(new Intent(cn, Congrats_Pop_Up.class)
                                        .putExtra("popup", achievement.getName() + "/" + achievement.getMsg())
                                        .putExtra("value", 3)
                                        .putExtra("pc", true)
                                        .putExtra("icon",achievement.getIcon()));
                                RealmService.UnlockMOdule(Realm.getDefaultInstance());
                                RealmService.UnlockSuperHero(3);
                                finishAffinity();
                            }
                        }
                        else {
                            complete=true;
                            AlertDialog.Builder builder = new AlertDialog.Builder(cn);
                            final View dialogView = getLayoutInflater().inflate(R.layout.pchallenge3, null);
                            builder.setView(dialogView);
                            final AlertDialog dialog = builder.create();

                            TextView yes = dialogView.findViewById(R.id.yes);
                            TextView no = dialogView.findViewById(R.id.dialog_wrong);

                            yes.setOnClickListener(vv->{
                                complete=false;
                            });
                            no.setOnClickListener(vv->{
                                finish();
                            });
                            dialog.show();

                        }
                    }





/*
                    AlertDialog.Builder builder = new AlertDialog.Builder(cn);
                    final View dialogView = getLayoutInflater().inflate(R.layout.dialog_challenge_result, null);
                    builder.setView(dialogView);
                    final AlertDialog dialog = builder.create();
                    TextView total = dialogView.findViewById(R.id.dialog_total);
                    TextView correct = dialogView.findViewById(R.id.dialog_correct);
                    TextView wrong = dialogView.findViewById(R.id.dialog_wrong);
                    total.setText(String.valueOf(pc1Models.size()));
                    correct.setText(String.valueOf(CorretAnswer));
                    wrong.setText(String.valueOf(WrongAnswer));
                    dialog.show();*/
               }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(cn, MainActivity.class));
        finishAffinity();
    }
}


//Shortcut  shift end last , logt , alt + up and down , ctrl + and - method collapse ,shift home first