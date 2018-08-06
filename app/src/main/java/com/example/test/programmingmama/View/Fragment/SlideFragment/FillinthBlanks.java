package com.example.test.programmingmama.View.Fragment.SlideFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.programmingmama.MainActivity;
import com.example.test.programmingmama.Model.NewModel.Blanks;
import com.example.test.programmingmama.Model.NewModel.Blanks_;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.Service.RealmService;
import com.example.test.programmingmama.View.ViewPager.CommunityMultipleModuleListViewPager;
import com.example.test.programmingmama.View.ViewPager.CommunityViewPager;

import io.realm.Realm;

public class FillinthBlanks extends Fragment {
    View v;
    LinearLayout hints;
    TextView modulename, fbt1, fbt2, fbt3, fbt4, fbtextprg, fboutput, fbquestion;
    ProgressBar bar;
    EditText fbed;
    Button fbrun;
    Context cn;
    String strTitles, strT1, strT2, strT3, strT4,  srtQuestion;
    String strSln = "";

    boolean status = false;
    int id;
    Realm realm;

    //todo Home FB
    String finish1 = " ";
    String FbStatus = " ";
    String FbId = " ";


    //todo Home List Fb
    String finish2 = "";
    String fmodule = "";
    String FbListStatus = " ";
    String ListFBId = " ";
    int ListId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_fillintheblanks, container, false);
        cn = getContext();
        realm = Realm.getDefaultInstance();
        init(cn);

        return v;
    }


    private void init(Context cn) {
        modulename = v.findViewById(R.id.fbmodulename);
        fbt1 = v.findViewById(R.id.fbt1);
        fbt2 = v.findViewById(R.id.fbt2);
        fbt3 = v.findViewById(R.id.fbt3);
        fbt4 = v.findViewById(R.id.fbt4);
        fbed = v.findViewById(R.id.fbed);
        fbquestion = v.findViewById(R.id.fbquestion);
        fboutput = v.findViewById(R.id.fboutput);
        fbrun = v.findViewById(R.id.fbrun);



        fbrun.setOnClickListener(v -> {
            if (status == true) {
                CallFinish();
            } else if (fbed.getText().toString().trim().equals("") || fbed.getText().toString().trim().equals(null)) {
                Toast.makeText(cn, "Please enter valid data", Toast.LENGTH_SHORT).show();
            } else {
                Animation slideUp = AnimationUtils.loadAnimation(cn, R.anim.fadein);
                fboutput.setVisibility(View.VISIBLE);
            if(strSln.equals("")||strSln.equals(null)||strSln.equals("null")){
                fboutput.setText(fbed.getText().toString());
            }else {
                fboutput.setText(strSln+fbed.getText().toString());
            }

                fboutput.startAnimation(slideUp);
                status = true;
                fbrun.setText("NEXT");
            }

        });
        SetUpTextView();

    }

    private void CallFinish() {
        if (finish1.equals("true")) {
            if (!FbStatus.equals("true")) {
                RealmService.HomeMark(realm, id,FbId, 100);
                CommunityViewPager.Updatetl();
                RealmService.UpdateHomeModule(cn,realm, id);
            } else {
                startActivity(new Intent(cn, MainActivity.class));
            }
        } else if (finish1.equals("false")) {
            if (!FbStatus.equals("true")) {
                RealmService.HomeMark(realm,id, FbId, 100);
                CommunityViewPager.Updatetl();
                CommunityViewPager.RightForward();
            } else {
                CommunityViewPager.RightForward();
            }

        }

        if (finish2.equals("true")) {
            RealmService.UnlockListModule(realm, id, ListId);
            if (fmodule.equals("true")) {
                if (!FbListStatus.equals("true")) {
                    RealmService.HomeListMark(realm, ListFBId, 100); //Add point to list module

                    RealmService.ADDLISTMODULEResult(realm,id, ListId); // Add sum to the List
                    CommunityMultipleModuleListViewPager.Updatetl(); // Add sum to the List
                    RealmService.UpdateListModule(realm, id);
                    startActivity(new Intent(cn, MainActivity.class));
                } else {
                    startActivity(new Intent(cn, MainActivity.class).putExtra("moduleName", strTitles));
                }

            } else {

                if (!FbListStatus.equals("true")) {
                    RealmService.HomeListMark(realm, ListFBId, 100); //Add point to list module

                    RealmService.ADDLISTMODULEResult(realm,id, ListId); // Add sum to the List
                    CommunityMultipleModuleListViewPager.Updatetl();
                    CommunityMultipleModuleListViewPager.FinishActivity();
                    RealmService.UnlockListModule(realm, id, ListId);
                    //   startActivity(new Intent(cn, CommunityMultipleModule.class));
                } else {
                    CommunityMultipleModuleListViewPager.FinishActivity();

                    // startActivity(new Intent(cn, CommunityMultipleModule.class));
                }

            }

        } else if (finish2.equals("false")) {
            if (!FbListStatus.equals("true")) {
                RealmService.HomeListMark(realm, ListFBId, 100);

                RealmService.ADDLISTMODULEResult(realm,id, ListId); // Add sum to the List
                CommunityMultipleModuleListViewPager.Updatetl();
                CommunityMultipleModuleListViewPager.RightForward();
            } else {
                CommunityMultipleModuleListViewPager.RightForward();
            }

        }
    }

    private void SetUpTextView() {
        if (strT1.equals("null")) {
            fbt1.setVisibility(View.INVISIBLE);
        } else {
            fbt1.setText(Html.fromHtml(strT1));
        }
        if (strT2.equals("null")) {
            fbt2.setVisibility(View.INVISIBLE);
        } else {
            fbt2.setText(Html.fromHtml(strT2));
        }
        if (strT3.equals("null")) {
            fbt3.setVisibility(View.INVISIBLE);
        } else {
            fbt3.setText(Html.fromHtml(strT3));
        }
        if (strT4.equals("null")) {
            fbt4.setVisibility(View.INVISIBLE);
        } else {
            fbt4.setVisibility(View.VISIBLE);
            fbt4.setText(Html.fromHtml(strT4));
        }
        fbquestion.setText(Html.fromHtml(srtQuestion));
        modulename.setText(strTitles);
    }

    public void setItem(int id, String name, String question, Blanks blanks, String finish, String FbStatus, String FbId) {
        strTitles = name;
        srtQuestion = question;
        strT1 = blanks.getTf1();
        strT2 = blanks.getTt1();
        strT3 = blanks.getTt2();
        strT4 = blanks.getTf2();
        this.finish1 = finish;
        this.id = id;
        this.FbStatus = FbStatus;
        this.FbId = FbId;
        if(blanks.getSoln()!=null){
            this.strSln=blanks.getSoln();
        }

    }

    public void setItem2(int id, String name, String des01, Blanks_ blanks, String finish, String fmodule, String ListFBId, String FbListStatus, int ListId) {
        strTitles = name;
        srtQuestion = des01;
        strT1 = blanks.getTf1();
        strT2 = blanks.getTt1();
        strT3 = blanks.getTt2();
        strT4 = blanks.getTf2();
        this.finish2 = finish;
        this.fmodule = fmodule;
        this.id = id;

        this.FbListStatus = FbListStatus;
        this.ListFBId = ListFBId;
        this.ListId = ListId;


        if(blanks.getSoln()!=null){
            this.strSln=blanks.getSoln();
        }
    }


}

    /*
         todo tag => findViewById => fb
    */
