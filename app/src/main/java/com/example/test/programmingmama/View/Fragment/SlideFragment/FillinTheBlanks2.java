package com.example.test.programmingmama.View.Fragment.SlideFragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.bumptech.glide.Glide;
import com.example.test.programmingmama.MainActivity;
import com.example.test.programmingmama.Model.Achievement;
import com.example.test.programmingmama.Model.NewModel.Blanks;
import com.example.test.programmingmama.Model.NewModel.Blanks_;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.PrefManager;
import com.example.test.programmingmama.Utils.Service.RealmService;
import com.example.test.programmingmama.View.ViewPager.CommunityMultipleModuleListViewPager;
import com.example.test.programmingmama.View.Activity.Congrats_Pop_Up;
import com.example.test.programmingmama.View.ViewPager.CommunityViewPager;

import io.realm.Realm;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

public class FillinTheBlanks2 extends Fragment {
    View v;
    TextView modulename, fbbt1, fbbt2, fbbt3, fbbt4, fbboutput, fbbquestion;

    EditText fbbed;
    Button fbbrun;
    Context cn;
    String strTitles, strT1, strT2, strT3, strT4, strSln, srtQuestion, strOutput, strOp;

    TextView top1, top2, top3;
    boolean status = false;
    int id;
    Realm realm;

    //TODO Home FB2
    String finish1 = " ";
    String FBBStatus = " ";
    String FBBID = " ";

    //TODO Home List FB2
    String finish2 = "";
    String fmodule = "";
    String FBBListStatus = " ";
    String ListFBBID = " ";
    int ListID;
    int Attempt = 1;

    int bid =0;
    String btype="";


    int i;
    int position = 0;

    PrefManager prefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_fillintheblanks2, container, false);
        cn = getContext();
        realm = Realm.getDefaultInstance();
        prefManager =new PrefManager(cn);
        init(cn);

        return v;
    }


    private void init(Context cn) {
        top1 = v.findViewById(R.id.top1);
        top2 = v.findViewById(R.id.top2);
        top3 = v.findViewById(R.id.top3);
        modulename = v.findViewById(R.id.fbbmodulename);
        fbbt1 = v.findViewById(R.id.fbbt1);
        fbbt2 = v.findViewById(R.id.fbbt2);
        fbbt3 = v.findViewById(R.id.fbbt3);
        fbbt4 = v.findViewById(R.id.fbbt4);
        fbbed = v.findViewById(R.id.fbbed);
        fbbquestion = v.findViewById(R.id.fbbquestion);
        fbboutput = v.findViewById(R.id.fbboutput);
        fbbrun = v.findViewById(R.id.fbbrun);
        fbbrun.setOnClickListener(v -> {
            if (status == true) {
                CallFinish();
            } else {
                if (strSln.trim().equals(fbbed.getText().toString().trim())) {
                    fbboutput.setVisibility(View.VISIBLE);
                    fbboutput.setText(Html.fromHtml(strOutput));
                    status = true;
                    fbbrun.setText("NEXT");

//                    if (new PrefManager(cn).SoundStatus()) {
//                        MediaPlayer mp = MediaPlayer.create(cn, R.raw.right);
//                        mp.start();
//                    }

                    TSnackbar snackbar = TSnackbar.make(getView(), "Correct Answer :) ", Snackbar.LENGTH_LONG);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(Color.parseColor("#FE8F01"));
                    TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);

                    textView.setTextColor(Color.WHITE);
                    snackbar.show();

                    prefManager.SetFIRST(prefManager.GetFIRST()+1);


                        if(btype.equals("p3")){
                            if(i==0) {
                                if (FBBStatus.equals("false")) {
                                    if (CommunityViewPager.getBCounter() == 0) {
                                        Achievement achievement = RealmService.findSuperHero(bid);
                                        startActivity(new Intent(cn, Congrats_Pop_Up.class)
                                                .putExtra("popup", achievement.getMsg() + "/" + achievement.getMsg())
                                                .putExtra("value", 3));
                                        RealmService.UnlockSuperHero(bid);
                                    }else {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(cn);
                                        final View dialogView = getLayoutInflater().inflate(R.layout.oopslayout,null);
                                        builder.setView(dialogView);
                                        final AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                }
                            }else {
                                    if(FBBListStatus.equals("false")) {
                                        if (CommunityMultipleModuleListViewPager.getBCounter() == 0) {
                                            Achievement achievement = RealmService.findSuperHero(bid);
                                            startActivity(new Intent(cn, Congrats_Pop_Up.class)
                                                    .putExtra("popup", achievement.getMsg() + "/" + achievement.getMsg())
                                                    .putExtra("value", 3));
                                            RealmService.UnlockSuperHero(bid);
                                        }else {

                                            AlertDialog.Builder builder = new AlertDialog.Builder(cn);
                                            final View dialogView = getLayoutInflater().inflate(R.layout.oopslayout,null);
                                            builder.setView(dialogView);
                                            final AlertDialog dialog = builder.create();
                                            dialog.show();
                                        }
                                    }
                            }

                    }

                }else if(fbbed.getText().toString().trim().equals("")||fbbed.getText().toString().trim().equals(null)){
                    Toast.makeText(cn, "Please enter valid data", Toast.LENGTH_SHORT).show();
                }
                else {

                   /* if (new PrefManager(cn).SoundStatus()) {
                        MediaPlayer mp = MediaPlayer.create(cn, R.raw.wrong);
                        mp.start();
                    }*/

                    TSnackbar snackbar = TSnackbar.make(getView(), "Oops,Wrong Answer. Please try again", Snackbar.LENGTH_LONG);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(Color.parseColor("#e91e63"));
                    TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);
                    snackbar.show();

                    snackbar.setAction("GO Back", vv -> {
                        if(i==0){
                            CommunityViewPager.BackForward();
                        }else {
                            CommunityMultipleModuleListViewPager.BackForward();
                        }
                    });
                    snackbar.show();

                    if(btype.equals("p1")||btype.equals("p2")){
                        if(i==0){
                            prefManager.SetGoBack(prefManager.GetGoBack()+1);
                            CommunityViewPager.AddCounter();
                        }else {
                            prefManager.SetGoBack(prefManager.GetGoBack()+1);
                            CommunityMultipleModuleListViewPager.AddCounter();
                        }
                    }

                    Attempt = Attempt + 1;
                    if(Attempt<=2){
                        prefManager.SetFIRST(prefManager.GetFIRST()+1);
                    }else if(Attempt>2){
                        prefManager.SetSECEND(prefManager.GetSECEND()+1);
                    }
                    fbbrun.setText("TRY AGAIN");
              }
            }
        });
        SetUpTextView();

        v.findViewById(R.id.hints).setOnClickListener(vv -> {
            LayoutInflater inflater2 = LayoutInflater.from(v.getContext());
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            final View dialogView = inflater2.inflate(R.layout.hintsdilog,null);
            builder.setView(dialogView);
            final AlertDialog dialog = builder.create();

            dialogView.findViewById(R.id.no).setOnClickListener(vvv -> dialog.dismiss());
            dialogView.findViewById(R.id.yes).setOnClickListener(vvv -> dialog.dismiss());
            builder.setView(dialogView);
            dialog.show();


        });

    }



    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            //Only manually call onResume if fragment is already visible
            //Otherwise allow natural fragment lifecycle to call onResume
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }


        if (btype.equals("p1")) {
            if (i == 0) {
                if (FBBStatus.equals("false")) {
                    IndicationPopup();
                }
            } else if (i == 1) {
                if (FBBListStatus.equals("false")) {
                    IndicationPopup();
                }
            }


            //INSERT CUSTOM CODE HERE
        }
    }

    private void IndicationPopup() {
        Achievement achievement =RealmService.findSuperHero(bid);
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




    private void CallFinish() {
        if (finish1.equals("true")) {
            if (!FBBStatus.equals("true")) {
                if (Attempt >= 2) {
                    RealmService.HomeMark(realm, id,FBBID, 50);
                    CommunityViewPager.Updatetl();
                    RealmService.UpdateHomeModule(cn,realm, id);
                    startActivity(new Intent(cn, MainActivity.class).putExtra("moduleName", strTitles));

                } else {
                    RealmService.HomeMark(realm, id,FBBID, 100);
                    CommunityViewPager.Updatetl();
                    RealmService.UpdateHomeModule(cn,realm, id);
                    startActivity(new Intent(cn, MainActivity.class).putExtra("moduleName", strTitles));

                }

            } else {
                startActivity(new Intent(cn, MainActivity.class).putExtra("moduleName", strTitles));

            }

        } else if (finish1.equals("false")) {
            if (!FBBStatus.equals("true")) {
                if (Attempt >= 2) {
                    RealmService.HomeMark(realm, id,FBBID, 50);
                    CommunityViewPager.Updatetl();
                    CommunityViewPager.RightForward();
                } else {
                    RealmService.HomeMark(realm, id,FBBID, 100);
                    CommunityViewPager.Updatetl();
                    CommunityViewPager.RightForward();
                }
            } else {
                CommunityViewPager.RightForward();
            }
        }


        if (finish2.equals("true")) {
            RealmService.UnlockListModule(realm,id,ListID);
            if (fmodule.equals("true")) {
                if (!FBBListStatus.equals("true")) {
                    if (Attempt >= 2) {
                        RealmService.HomeListMark(realm,ListFBBID, 50); //Add point to list module
                        RealmService.ADDLISTMODULEResult(realm,id, ListID);
                        CommunityMultipleModuleListViewPager.Updatetl();
                        RealmService.UpdateListModule(realm, id);
                        startActivity(new Intent(cn, MainActivity.class).putExtra("moduleName", strTitles));
                    } else {
                        RealmService.HomeListMark(realm, ListFBBID, 100); //Add point to list module
                        RealmService.ADDLISTMODULEResult(realm,id, ListID);
                        CommunityMultipleModuleListViewPager.Updatetl();
                        RealmService.UpdateListModule(realm, id);
                        startActivity(new Intent(cn, MainActivity.class).putExtra("moduleName", strTitles));
                    }
                } else {
                    startActivity(new Intent(cn, MainActivity.class).putExtra("moduleName", strTitles));

                }
            } else {

                if (!FBBListStatus.equals("true")) {
                    if (Attempt >= 2) {
                        RealmService.HomeListMark(realm, ListFBBID, 50); //Add point to list module
                        RealmService.ADDLISTMODULEResult(realm,id, ListID);
                        CommunityMultipleModuleListViewPager.Updatetl();
                        CommunityMultipleModuleListViewPager.FinishActivity();

                       // startActivity(new Intent(cn, CommunityMultipleModule.class));
                    } else {
                        RealmService.HomeListMark(realm, ListFBBID, 100); //Add point to list module

                        RealmService.ADDLISTMODULEResult(realm,id, ListID);
                        CommunityMultipleModuleListViewPager.Updatetl();
                        CommunityMultipleModuleListViewPager.FinishActivity();
                       // startActivity(new Intent(cn, CommunityMultipleModule.class));
                    }
                } else {
                    CommunityMultipleModuleListViewPager.FinishActivity();

                   // startActivity(new Intent(cn, CommunityMultipleModule.class));
                }

            }

        } else if (finish2.equals("false")) {

            if (!FBBListStatus.equals("true")) {
                if (Attempt >= 2) {
                    RealmService.HomeListMark(realm, ListFBBID, 50);
                    RealmService.ADDLISTMODULEResult(realm,id, ListID);
                    CommunityMultipleModuleListViewPager.Updatetl();
                    CommunityMultipleModuleListViewPager.RightForward();
                } else {
                    RealmService.HomeListMark(realm, ListFBBID, 100);
                    RealmService.ADDLISTMODULEResult(realm,id, ListID);
                    CommunityMultipleModuleListViewPager.Updatetl();
                    CommunityMultipleModuleListViewPager.RightForward();
                }
            } else {
                CommunityMultipleModuleListViewPager.RightForward();
            }

        }
    }

    private void TestPyView() {
        if (strOp.equals("null")) {
            top1.setVisibility(View.GONE);
            top2.setVisibility(View.GONE);
            top3.setVisibility(View.GONE);

        } else {
            String[] parts = strOp.split("/");
            String op1 = parts[0];
            String op2 = parts[1];
            String op3 = parts[2];

            top1.setText(op1);
            top2.setText(op2);
            top3.setText(op3);


            top1.setOnClickListener(v -> {
                if(position==2){
                    top2.setText(op2);
                }
                if (position ==3){
                    top3.setText(op3);
                }
                if (fbbed.getText().toString().equals("") || fbbed.getText().toString().equals(null) || !op1.equals(fbbed.getText().toString().trim())) {
                    fbbed.setText(op1);
                    top1.setText(" ");
                    position=1;
                } else if (op1.equals(fbbed.getText().toString().trim())) {
                    top1.setText(op1);
                    fbbed.setText("");
                    position=0;
                }


            });
            top2.setOnClickListener(v -> {
                if(position==1){
                    top1.setText(op1);
                }
                if (position ==3){
                    top3.setText(op3);
                }

                if (fbbed.getText().equals("") || fbbed.getText().equals(null) || !op2.equals(fbbed.getText().toString().trim())) {
                    fbbed.setText(op2);
                    top2.setText(" ");
                    position=2;
                } else if (op2.equals(fbbed.getText().toString().trim())) {
                    top2.setText(op2);
                    fbbed.setText("");
                    position=0;
                }



            });
            top3.setOnClickListener(v -> {

                if(position==1){
                    top1.setText(op1);
                }
                if (position ==2){
                    top2.setText(op2);
                }

                if (fbbed.getText().equals("") || fbbed.getText().equals(null) || !op3.equals(fbbed.getText().toString().trim())) {
                    fbbed.setText(op3);
                    top3.setText(" ");
                    position=3;
                } else if (op3.equals(fbbed.getText().toString().trim())) {
                    fbbed.setText("");
                    top3.setText(op3);
                    position=0;
                }


            });
        }

    }

    private void SetUpTextView() {
        if (strT1.equals("null")) {
            fbbt1.setVisibility(View.INVISIBLE);
        } else {
            fbbt1.setText(Html.fromHtml(strT1));
        }
        if (strT2.equals("null")) {
            fbbt2.setVisibility(View.INVISIBLE);
        } else {
            fbbt2.setText(Html.fromHtml(strT2));
        }
        if (strT3.equals("null")) {
            fbbt3.setVisibility(View.INVISIBLE);
        } else {
            fbbt3.setText(Html.fromHtml(strT3));
        }
        if (strT4.equals("null")) {
            fbbt4.setVisibility(View.INVISIBLE);
        } else {
            fbbt4.setVisibility(View.VISIBLE);
            fbbt4.setText(Html.fromHtml(strT4));
        }
        fbbquestion.setText(Html.fromHtml(srtQuestion));
        modulename.setText(strTitles);

        TestPyView();
    }

    public void setItem2(String name, String des01, Blanks_ blanks, String finish, String fmodule, int id, String ListFBBID, String FBBListStatus, int ListID,int bid,String btype) {
        strTitles = name;
        srtQuestion = des01;
        strT1 = blanks.getTf1();
        strT2 = blanks.getTt1();
        strT3 = blanks.getTt2();
        strT4 = blanks.getTf2();
        strSln = blanks.getSoln();
        strOutput = blanks.getOutput();
        strOp = blanks.getOutput();
        strOp = blanks.getOp();
        this.finish2 = finish;
        this.id = id;
        this.fmodule = fmodule;
        this.FBBListStatus = FBBListStatus;
        this.ListID = ListID;
        this.ListFBBID = ListFBBID;
        i = 1;
        if(btype !=null){
            this.bid=bid;
            this.btype=btype;
        }
    }


    public void setItem(int id, String name, String des01, Blanks blanks, String finish, String FBBStatus, String FBBID,int bid,String btype) {
        strTitles = name;
        srtQuestion = des01;
        strT1 = blanks.getTf1();
        strT2 = blanks.getTt1();
        strT3 = blanks.getTt2();
        strT4 = blanks.getTf2();
        strSln = blanks.getSoln();
        strOutput = blanks.getOutput();
        strOp = blanks.getOp();
        this.finish1 = finish;
        this.id = id;

        this.FBBID = FBBID;
        this.FBBStatus = FBBStatus;
        i = 0;
        if(btype !=null){
            this.bid=bid;
            this.btype=btype;
        }
    }
}

 /*
         todo tag => findViewById => fb
    */
