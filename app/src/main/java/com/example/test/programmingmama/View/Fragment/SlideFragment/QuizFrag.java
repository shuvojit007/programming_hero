package com.example.test.programmingmama.View.Fragment.SlideFragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.bumptech.glide.Glide;
import com.example.test.programmingmama.MainActivity;
import com.example.test.programmingmama.Model.Achievement;
import com.example.test.programmingmama.Model.NewModel.Option;
import com.example.test.programmingmama.Model.NewModel.Quiz;
import com.example.test.programmingmama.Model.NewModel.Quiz_;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.PrefManager;
import com.example.test.programmingmama.Utils.Service.RealmService;
import com.example.test.programmingmama.View.ViewPager.CommunityMultipleModuleListViewPager;
import com.example.test.programmingmama.View.Activity.Congrats_Pop_Up;
import com.example.test.programmingmama.View.ViewPager.CommunityViewPager;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;
import io.realm.Realm;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

public class QuizFrag extends Fragment {
    View v;
    Context cn;
    TextView ques;
    RadioButton q1, q2, q3;
    Button run;

    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCd;

    TextView quiztextprogress;
    RadioGroup qradiogrp;

    int i = 0;
    private CountDownTimer countDownTimer;
    private ProgressBar progressBarCircle;
    private long timeCountInMilliSeconds = 1 * 30000;

    private boolean answered;

    int bid =0;
    String btype="";

    String strTitle, strQuestion, strSoln;


    String fmodule = "";
    List<Option> options;
    Quiz quiz;
    Quiz_ quiz2;

    //Todo Home =========
    String finish1 = " ";
    String HomeQuizStatus = " ";
    String HomeQuizID = " ";

    //todo Home List ============
    String finish2 = "";
    String HomeQuizListStatus = " ";
    String HomeQuizListID = " ";
    int HOmeListID;


    CodeView code_view;
    String code="";



    Realm realm;
    int id;
    boolean status = false;

    int Attempt = 1;

     boolean isRunning = false;
     PrefManager prefManager;
    TextView hintstext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_quiz, container, false);
        cn = getContext();
        realm = Realm.getDefaultInstance();
        prefManager =new PrefManager(cn);
        if (quiz != null) {
            init(cn);
        } else {
            init2(cn);
        }



        hintstext = v.findViewById(R.id.hintstext);
        v.findViewById(R.id.hints).setOnClickListener(vv -> {
            LayoutInflater inflater2 = LayoutInflater.from(v.getContext());
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            final View dialogView = inflater2.inflate(R.layout.hintsdilog,null);
            builder.setView(dialogView);
            final AlertDialog dialog = builder.create();

            dialogView.findViewById(R.id.no).setOnClickListener(vvv -> dialog.dismiss());
            dialogView.findViewById(R.id.yes).setOnClickListener(vvv -> {
                dialog.dismiss();
                hintstext.setText("This is your hints");

            });
            builder.setView(dialogView);
            dialog.show();


        });

        return v;
    }


    @Override
    public void setUserVisibleHint(boolean visible) {
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

        //todo programmin hero badge hv three type p1 indication p2 middle

        if (btype.equals("p1")) {
            if (i == 0) {
                if (HomeQuizStatus.equals("false")) {
                    IndicationPopup();
                }else {
                    if (!answered) {
                        if(!isRunning){
                            startCountDownTimer();
                        }
                    }
                }
            } else if (i == 1) {
                if (HomeQuizListStatus.equals("false")) {
                    IndicationPopup();
                }else {
                    if (!answered) {
                        if(!isRunning){
                            startCountDownTimer();
                        }
                    }
                }
            }
        }else {
            if (!answered) {
                if(!isRunning){
                    startCountDownTimer();
                }
            }
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

        dialog.setOnDismissListener(dialog1 -> {
            if (!answered) {
                if(!isRunning){
                    startCountDownTimer();
                }

            }
        });
    }



    private void CallFinish() {

        if(countDownTimer!=null){
            countDownTimer.cancel();
        }



        if (finish1.equals("true")) {
            if (!HomeQuizStatus.equals("true")) {
                if (Attempt >= 2) {


                    RealmService.HomeMark(realm, id,HomeQuizID, 50);
                    CommunityViewPager.Updatetl();
                    RealmService.UpdateHomeModule(cn,realm, id);
                    startActivity(new Intent(cn, MainActivity.class).putExtra("moduleName", strTitle));
                } else {
                    RealmService.HomeMark(realm, id,HomeQuizID, 100);
                    RealmService.UpdateHomeModule(cn,realm, id);
                    startActivity(new Intent(cn, MainActivity.class).putExtra("moduleName", strTitle));

                }
            } else {
                startActivity(new Intent(cn, MainActivity.class).putExtra("moduleName", strTitle));
            }

        } else if (finish1.equals("false")) {
            if (!HomeQuizStatus.equals("true")) {
                if (Attempt >= 2) {
                    RealmService.HomeMark(realm, id,HomeQuizID, 50);
                    CommunityViewPager.Updatetl();
                    CommunityViewPager.RightForward();
                } else {
                    RealmService.HomeMark(realm, id,HomeQuizID, 100);
                    CommunityViewPager.Updatetl();
                    CommunityViewPager.RightForward();
                }
            } else {
                CommunityViewPager.RightForward();
            }


        }

        if (finish2.equals("true")) {
            RealmService.UnlockListModule(realm, id, HOmeListID);
            if (fmodule.equals("false")) {
                if (!HomeQuizListStatus.equals("true")) {
                    if (Attempt >= 2) {
                        RealmService.HomeListMark(realm, HomeQuizListID, 50); //Add point to list module

                        RealmService.ADDLISTMODULEResult(realm,id, HOmeListID);
                        CommunityMultipleModuleListViewPager.Updatetl();
                        CommunityMultipleModuleListViewPager.FinishActivity();
                    } else {
                        RealmService.HomeListMark(realm,HomeQuizListID, 100); //Add point to list module
                        RealmService.ADDLISTMODULEResult(realm,id, HOmeListID);
                        CommunityMultipleModuleListViewPager.Updatetl();
                        CommunityMultipleModuleListViewPager.FinishActivity();

                    }

                } else {
                    CommunityMultipleModuleListViewPager.FinishActivity();
                    //   startActivity(new Intent(cn, MainActivity.class).putExtra("moduleName", strTitle));
                }

            } else {

                if (!HomeQuizListStatus.equals("true")) {
                    if (Attempt >= 2) {
                        RealmService.HomeListMark(realm,HomeQuizListID, 50); //Add point to list module
                        RealmService.ADDLISTMODULEResult(realm,id, HOmeListID);
                        CommunityMultipleModuleListViewPager.Updatetl();
                        RealmService.UpdateListModule(realm, id);
                        startActivity(new Intent(cn, MainActivity.class).putExtra("moduleName", strTitle));

                    } else {
                        RealmService.HomeListMark(realm, HomeQuizListID, 100); //Add point to list module

                        RealmService.ADDLISTMODULEResult(realm,id, HOmeListID);
                        CommunityMultipleModuleListViewPager.Updatetl();
                        RealmService.UpdateListModule(realm, id);
                        startActivity(new Intent(cn, MainActivity.class).putExtra("moduleName", strTitle));

                    }
                } else {
                    startActivity(new Intent(cn, MainActivity.class).putExtra("moduleName", strTitle));
                }

            }
        } else if (finish2.equals("false")) {
            //  getActivity().finish();
            if (!HomeQuizListStatus.equals("true")) {
                if (Attempt >= 2) {
                    RealmService.HomeListMark(realm, HomeQuizListID, 50);
                    CommunityMultipleModuleListViewPager.Updatetl();
                    CommunityMultipleModuleListViewPager.RightForward();
                } else {
                    RealmService.HomeListMark(realm, HomeQuizListID, 100);
                    RealmService.ADDLISTMODULEResult(realm,id, HOmeListID);
                    CommunityMultipleModuleListViewPager.Updatetl();
                    CommunityMultipleModuleListViewPager.RightForward();
                }
            } else {
                CommunityMultipleModuleListViewPager.RightForward();
            }


        }
    }


    private void init2(Context cn) {
        if(!code.equals("null")&&!code.equals("")){
            code_view =v.findViewById(R.id.code_view);
            code_view.setVisibility(View.VISIBLE);
            Toast.makeText(cn, code, Toast.LENGTH_SHORT).show();
            code_view.setOptions(Options.Default.get(cn)
                    .withLanguage("python")
                    .withCode(code)
                    .withShadows()
                    .withTheme(ColorTheme.MONOKAI));
        }
        qradiogrp = v.findViewById(R.id.fragqradiogrp);
        ques = v.findViewById(R.id.fragquestion);
        q1 = v.findViewById(R.id.fragq1);
        q2 = v.findViewById(R.id.fragq2);
        q3 = v.findViewById(R.id.fragq3);

        q1.setText(Html.fromHtml(quiz2.getOption().get(0).getSet()));
        q2.setText(Html.fromHtml(quiz2.getOption().get(1).getSet()));
        q3.setText(Html.fromHtml(quiz2.getOption().get(2).getSet()));
//
        ques.setText(Html.fromHtml(strQuestion));

        progressBarCircle = v.findViewById(R.id.fragprogressBarCircle);
        quiztextprogress = v.findViewById(R.id.fragquiztextprogress);
        quiztextprogress.setVisibility(View.VISIBLE);
        quiztextprogress.setText(" 30s ");
        //  progressBarCircle.setVisibility(View.VISIBLE);

        textColorDefaultRb = q1.getTextColors();
        textColorDefaultCd = quiztextprogress.getTextColors();

        run = v.findViewById(R.id.fragrun);
        //  run.setBackgroundColor(Color.GREEN);
        run.setOnClickListener(v -> {
            if (!status) {
                if (!answered) {
                    if (q1.isChecked() || q2.isChecked() || q3.isChecked()) {
                        if (q1.isChecked()) {
                            checkAnswer(q1.getText().toString(), 1);
                        } else if (q2.isChecked()) {
                            checkAnswer(q2.getText().toString(), 2);
                        } else {
                            checkAnswer(q3.getText().toString(), 3);
                        }
                    } else {
                        Toast.makeText(cn, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                CallFinish();
            }

        });
     //   startCountDownTimer();
    }

    private void init(Context cn) {

        if(!code.equals("null")&&!code.equals("")){
            code_view =v.findViewById(R.id.code_view);
            code_view.setVisibility(View.VISIBLE);
            code_view.setOptions(Options.Default.get(cn)
                    .withLanguage("python")
                    .withCode(code)
                    .withShadows()
                    .withTheme(ColorTheme.MONOKAI));
        }

        qradiogrp = v.findViewById(R.id.fragqradiogrp);
        ques = v.findViewById(R.id.fragquestion);
        q1 = v.findViewById(R.id.fragq1);
        q2 = v.findViewById(R.id.fragq2);
        q3 = v.findViewById(R.id.fragq3);

        q1.setText(quiz.getOption().get(0).getSet());
        q2.setText(quiz.getOption().get(1).getSet());
        q3.setText(quiz.getOption().get(2).getSet());
//
        ques.setText(strQuestion);

        progressBarCircle = v.findViewById(R.id.fragprogressBarCircle);
        quiztextprogress = v.findViewById(R.id.fragquiztextprogress);
        quiztextprogress.setVisibility(View.VISIBLE);
        //   progressBarCircle.setVisibility(View.VISIBLE);

        textColorDefaultRb = q1.getTextColors();
        textColorDefaultCd = quiztextprogress.getTextColors();

        run = v.findViewById(R.id.fragrun);
        // run.setBackgroundColor(Color.GREEN);
        run.setOnClickListener(v -> {
            if (!status) {
                if (!answered) {
                    if (q1.isChecked() || q2.isChecked() || q3.isChecked()) {
                        if (q1.isChecked()) {
                            checkAnswer(q1.getText().toString(), 1);
                        } else if (q2.isChecked()) {
                            checkAnswer(q2.getText().toString(), 2);
                        } else {
                            checkAnswer(q3.getText().toString(), 3);
                        }
                    } else {
                        Toast.makeText(cn, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                CallFinish();
            }

        });
      //  startCountDownTimer();
    }

    private void checkAnswer(String ans, int Index) {
        if (ans.trim().toString().equals(strSoln.trim().toString())) {
            answered = true;
            q1.setTextColor(Color.RED);
            q2.setTextColor(Color.RED);
            q3.setTextColor(Color.RED);
            switch (Index) {
                case 1:
                    q1.setTextColor(Color.GREEN);
                    break;
                case 2:
                    q2.setTextColor(Color.GREEN);
                    break;
                case 3:
                    q3.setTextColor(Color.GREEN);
                    break;
            }
            if (countDownTimer != null)
                countDownTimer.cancel();

//            if (new PrefManager(cn).SoundStatus()) {
//                MediaPlayer mp = MediaPlayer.create(cn, R.raw.right);
//                mp.start();
//            }


            // run.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            TSnackbar snackbar = TSnackbar.make(getView(), "Correct Answer :) ", Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(Color.parseColor("#FE8F01"));

            TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);

            textView.setTextColor(Color.WHITE);
            snackbar.show();

            if(isRunning){
                if(btype.equals("p3")){
                    Log.d("SuperHero", "btype p3  ");
                    if(i==0){

                        if(HomeQuizStatus.equals("false")) {
                            if (CommunityViewPager.getBCounter() == 0) {
                                Log.d("SuperHero", "getBCounter: " + CommunityViewPager.getBCounter());
                                Achievement achievement = RealmService.findSuperHero(bid);
                                startActivity(new Intent(cn, Congrats_Pop_Up.class)
                                        .putExtra("popup", achievement.getName()+"/"+achievement.getMsg())
                                        .putExtra("value", 3)
                                        .putExtra("icon",achievement.getIcon()));
                                RealmService.UnlockSuperHero(bid);
                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(cn);
                                final View dialogView = getLayoutInflater().inflate(R.layout.oopslayout,null);
                                builder.setView(dialogView);
                                final AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }else {


                            Log.d("SuperHero", "Status true  ");
                        }


                    }else {
                            Log.d("SuperHero ", "i=1 p3  ");
                            if (HomeQuizListStatus.equals("false")) {

                                if (CommunityMultipleModuleListViewPager.getBCounter() == 0) {
                                    Log.d("SuperHero", "getBListCounter: "+CommunityViewPager.getBCounter());

                                    Achievement achievement = RealmService.findSuperHero(bid);
                                    startActivity(new Intent(cn, Congrats_Pop_Up.class)
                                            .putExtra("popup", achievement.getName()  + "/" + achievement.getMsg())
                                            .putExtra("value", 3)
                                            .putExtra("icon",achievement.getIcon()));
                                    RealmService.UnlockSuperHero(bid);
                                }else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(cn);
                                    final View dialogView = getLayoutInflater().inflate(R.layout.oopslayout,null);
                                    builder.setView(dialogView);
                                    final AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            }else {


                                Log.d("SuperHero", "HomeQuizListStatus true  ");
                            }
                        }

            }

            }
            prefManager.SetFIRST(prefManager.GetFIRST()+1);

            run.setText("Next");
            status = true;
        } else {
//            if (new PrefManager(cn).SoundStatus()) {
//                MediaPlayer mp = MediaPlayer.create(cn, R.raw.wrong);
//                mp.start();
//            }


            TSnackbar snackbar = TSnackbar.make(getView(), "Oops,Wrong Answer. Please try again", Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(Color.parseColor("#e91e63"));
            TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.setAction("GO Back", v -> {
                if (i == 0) {
                    prefManager.SetGoBack(prefManager.GetGoBack()+1);
                    CommunityViewPager.BackForward();
                } else {
                    prefManager.SetGoBack(prefManager.GetGoBack()+1);
                    CommunityMultipleModuleListViewPager.BackForward();
                }
            });

            snackbar.show();


            Attempt = Attempt + 1;

            if(Attempt<=2){
                prefManager.SetFIRST(prefManager.GetFIRST()+1);
            }else if(Attempt>2){
                prefManager.SetSECEND(prefManager.GetSECEND()+1);
            }

            if(btype.equals("p1")||btype.equals("p2")){
                if(i==0){
                    CommunityViewPager.AddCounter();
                }else {
                    CommunityMultipleModuleListViewPager.AddCounter();
                }
            }
            run.setText("Try Again");
        }


    }


    private void startCountDownTimer() {

        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                isRunning = true;
                quiztextprogress.setText(hmsTimeFormatter(millisUntilFinished) + "s");

                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));

            }

            @Override
            public void onFinish() {
                isRunning = false;
                quiztextprogress.setText(hmsTimeFormatter(timeCountInMilliSeconds));
                // call to initialize the progress bar values
                setProgressBarValues();

            }

        }.start();
        countDownTimer.start();
    }

    private void setProgressBarValues() {

        progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);
    }

    private String hmsTimeFormatter(long milliSeconds) {
        String hms = String.format("%02d",
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));
        return hms;


    }


    public void setItem(String title, int id, Quiz quiz, String finish, String HomeFbStatus, String HomeFbID,int bid,String btype) {
        strTitle = title;
        strQuestion = quiz.getQuestion();
        this.quiz = quiz;
        strSoln = quiz.getSolution();
        this.finish1 = finish;
        this.id = id;
        this.HomeQuizStatus = HomeFbStatus;
        this.HomeQuizID = HomeFbID;
        i = 0;
        if(btype !=null){
            this.bid=bid;
            this.btype=btype;
        }

        if (quiz.getCode()!=null){
            if (!quiz.getCode().equals("null")){
                this.code =quiz.getCode();
            }
        }


    }

    public void setItem2(int id, String mtitle, Quiz_ quiz2, String finish, String fmodule,String HomeQuizListID,  String HomeQuizListStatus, int HOmeListID,int bid,String btype) {
        strTitle = mtitle;
        strQuestion = quiz2.getQuestion();
        this.quiz2 = quiz2;
        strSoln = quiz2.getSolution();
        this.finish2 = finish;
        this.id = id;
        this.fmodule = fmodule;
        this.HomeQuizListID = HomeQuizListID;
        this.HomeQuizListStatus = HomeQuizListStatus;
        this.HOmeListID = HOmeListID;
        i = 1;


        if(btype !=null){
            this.bid=bid;
            this.btype=btype;
        }

        if (quiz2.getCode()!=null){
            if (!quiz2.getCode().equals("null")){
                this.code =quiz2.getCode();
            }
        }
    }
}



/*
    todo tag -> findViewById ---> frag
*/
