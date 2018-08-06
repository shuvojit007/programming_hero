package com.example.test.programmingmama.View.Fragment.SlideFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.test.programmingmama.MainActivity;
import com.example.test.programmingmama.Model.Achievement;
import com.example.test.programmingmama.Model.NewModel.Blanks;
import com.example.test.programmingmama.Model.NewModel.Blanks_;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.Service.RealmService;
import com.example.test.programmingmama.View.ViewPager.CommunityMultipleModuleListViewPager;
import com.example.test.programmingmama.View.ViewPager.CommunityViewPager;

import io.realm.Realm;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

public class FillintheblanksMath extends Fragment {
    View v;
    TextView modulename, fbAdd, fbMinus, fbDiv, fbMul,fbbquestion,fbbbt1, fbbbt2, fbbbt3, fbbbt4,fbbboutput;

    TextView fbbbop;

    Context cn;
    String strTitles,strT1,strT2,strT3,strT4,strSln,srtQuestion,strv1,strv2,strv3;

    int v1=0;
    int v2=0;

    boolean status =false;
    Button fbbmath;
    Realm realm;
    int id;

    //todo Home FBM
    String finish1= " ";
    String FBMSatus = "";
    String FBMID = " ";

    //todo Home List FBM Module
    String finish2 = "";
    String fmodule="";
    String FBMListSatus = "";
    String ListFBMID = " ";
    int ListID;

    int bid =0;
    int i = 0;
    String btype="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.frag_fillintheblanks_math,container,false);
        cn=getContext();
        realm =Realm.getDefaultInstance();
        init(cn);
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
        return v;

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
                if (FBMSatus.equals("false")) {
                    IndicationPopup();
                }
            } else if (i == 1) {
                if (FBMListSatus.equals("false")) {
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
        if(finish1.equals("true")){
            if(!FBMSatus.equals("true")){
                RealmService.HomeMark(realm, id,FBMID, 100);
                CommunityViewPager.Updatetl();
                RealmService.UpdateHomeModule(cn,realm, id);
                startActivity(new Intent(cn, MainActivity.class).putExtra("moduleName",strTitles));

            }else {
                startActivity(new Intent(cn, MainActivity.class).putExtra("moduleName",strTitles));

            }

        }else if(finish1.equals("false")){
            if(!FBMSatus.equals("true")){
                RealmService.HomeMark(realm, id,FBMID, 100);
                CommunityViewPager.Updatetl();
                CommunityViewPager.RightForward();
            }else {
                CommunityViewPager.RightForward();
            }

        }

        if(finish2.equals("true")){
            RealmService.UnlockListModule(realm,id,ListID);
            if(fmodule.equals("true")){

                if(!FBMListSatus.equals("true")){
                    RealmService.HomeListMark(realm, ListFBMID, 100); //Add point to list module

                    RealmService.ADDLISTMODULEResult(realm,id,ListID); // Add sum to the List
                    CommunityMultipleModuleListViewPager.Updatetl();
                    RealmService.UpdateListModule(realm, id);
                    startActivity(new Intent(cn, MainActivity.class).putExtra("moduleName",strTitles));
                }else {
                    startActivity(new Intent(cn, MainActivity.class).putExtra("moduleName",strTitles));

                }
            }else {
                if(!FBMListSatus.equals("true")){
                    RealmService.HomeListMark(realm, ListFBMID, 100); //Add point to list module

                    RealmService.ADDLISTMODULEResult(realm,id,ListID); // Add sum to the List
                    CommunityMultipleModuleListViewPager.Updatetl();
                    CommunityMultipleModuleListViewPager.FinishActivity();

                  //  startActivity(new Intent(cn, CommunityMultipleModule.class));
                }else {
                    CommunityMultipleModuleListViewPager.FinishActivity();

                 //   startActivity(new Intent(cn, CommunityMultipleModule.class));
                }

            }
        }else if(finish2.equals("false")){
            if (!FBMListSatus.equals("true")) {
                RealmService.HomeListMark(realm,ListFBMID, 100);
                RealmService.ADDLISTMODULEResult(realm,id,ListID); // Add sum to the List
                CommunityMultipleModuleListViewPager.Updatetl();
                CommunityMultipleModuleListViewPager.RightForward();
            } else {
                CommunityMultipleModuleListViewPager.RightForward();

            }
         }
    }

    private void init(Context cn) {
        fbbmath =v.findViewById(R.id.fbbmathrun);
        modulename = v.findViewById(R.id.fbbbmodulename);
        fbAdd=v.findViewById(R.id.fbbbadd);
        fbDiv =v.findViewById(R.id.fbbbdiv);
        fbMinus = v.findViewById(R.id.fbbbminus);
        fbMul = v.findViewById(R.id.fbbbmul);
        fbbquestion =v.findViewById(R.id.fbbbquestion);
        fbbbop =v.findViewById(R.id.fbbbop);
        fbbboutput=v.findViewById(R.id.fbbboutput);
        fbbbt1 =v.findViewById(R.id.fbbbt1);
        fbbbt2 =v.findViewById(R.id.fbbbt2);
        fbbbt3 =v.findViewById(R.id.fbbbt3);
        fbbbt4 =v.findViewById(R.id.fbbbt4);
        SetUpTextView();
        fbbmath.setOnClickListener(vv->{
            if(status){
                CallFinish();
            }else {
                Toast.makeText(cn, "Please Select anyting first", Toast.LENGTH_SHORT).show();
            }
        });

        fbAdd.setOnClickListener(v -> {
            status =true;
            fbbbop.setText("+");
            int res= v1+v2;
            fbbboutput.setVisibility(View.VISIBLE);
            fbbboutput.setText(Html.fromHtml(String.valueOf(res)));
        });

        fbDiv.setOnClickListener(v -> {
            status =true;
            fbbbop.setText("/");
            int res= v1/v2;
            fbbboutput.setVisibility(View.VISIBLE);
            fbbboutput.setText(String.valueOf(res));
        });

        fbMinus.setOnClickListener(v -> {
            status =true;
            fbbbop.setText("-");
            int res= v1-v2;
            fbbboutput.setVisibility(View.VISIBLE);
            fbbboutput.setText(String.valueOf(res));
        });

        fbMul.setOnClickListener(v -> {
            status =true;
            fbbbop.setText("*");
            int res= v1*v2;
            fbbboutput.setVisibility(View.VISIBLE);
            fbbboutput.setText(String.valueOf(res));
        });

    }

    private void SetUpTextView() {
        if(strT1.equals("null")){
            fbbbt1.setVisibility(View.INVISIBLE);
        }else {
            fbbbt1.setText(Html.fromHtml(strT1));
        }
        if(strT2.equals("null")){
            fbbbt2.setVisibility(View.INVISIBLE);
        }else {
            fbbbt2.setText(Html.fromHtml(strT2));
        }
        if(strT3.equals("null")){
            fbbbt3.setVisibility(View.INVISIBLE);
        }else {
            fbbbt3.setText(Html.fromHtml(strT3));
        }
        if(strT4.equals("null")){
            fbbbt4.setVisibility(View.INVISIBLE);
        }else {
            fbbbt4.setVisibility(View.VISIBLE);
            fbbbt4.setText(Html.fromHtml(strT4));
        }
        fbbquestion.setText(Html.fromHtml(srtQuestion));
        modulename.setText(strTitles);

    }


    public void setItem2(String name, String des01, Blanks_ blanks,String finish,String fmodule,int id,String ListFBMID,String FBMListSatus ,int ListID,int bid,String btype ) {
        strTitles =name;
        srtQuestion=des01;
        strT1 =blanks.getTf1();
        strT2 =blanks.getTt1();
        strT3 =blanks.getTt2();
        strT4 = blanks.getTf2();
        strSln =blanks.getSoln();
        v1=blanks.getV1();
        v2=blanks.getV2();
        finish2=finish;
        this.fmodule =fmodule;
        this.id=id;
        this.ListID=ListID;
        this.FBMListSatus=FBMListSatus;
        this.ListFBMID=ListFBMID;
        if(btype !=null){
            this.bid=bid;
            this.btype=btype;
        }

        i = 1;
    }

    public void setItem(int id, String name, String des01, Blanks blanks,  String finish,String FBMSatus, String FBMID,int bid,String btype) {
        strTitles =name;
        srtQuestion=des01;
        strT1 =blanks.getTf1();
        strT2 =blanks.getTt1();
        strT3 =blanks.getTt2();
        strT4 = blanks.getTf2();
        strSln =blanks.getSoln();
        v1=blanks.getV1();
        v2=blanks.getV2();
        finish1=finish;
        this.id =id;
        this.FBMID =FBMID;
        this.FBMSatus =FBMSatus;
        if(btype !=null){
            this.bid=bid;
            this.btype=btype;
        }

        i = 0;
    }
}
