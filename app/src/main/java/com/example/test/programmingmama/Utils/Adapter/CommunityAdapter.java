package com.example.test.programmingmama.Utils.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.test.programmingmama.Model.FinalChallenge.FinalChallenge;
import com.example.test.programmingmama.Model.NewModel.Home;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.PrefManager;
import com.example.test.programmingmama.View.Activity.Certificate;
import com.example.test.programmingmama.View.Activity.Challenge;
import com.example.test.programmingmama.View.Activity.Challenge3_Activity;
import com.example.test.programmingmama.View.Activity.CommunityMultipleModule;
import com.example.test.programmingmama.View.Activity.Explore_More;
import com.example.test.programmingmama.View.Activity.FeedBack_Module;
import com.example.test.programmingmama.View.Activity.Final_Challenge;
import com.example.test.programmingmama.View.Activity.ProgrammingChallenge;
import com.example.test.programmingmama.View.ViewPager.CommunityViewPager;
import com.squareup.picasso.Picasso;
//import com.stepstone.apprating.AppRatingDialog;
//import com.stepstone.apprating.listener.RatingDialogListener;

import java.util.List;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

public class CommunityAdapter extends RecyclerView.Adapter {
    private static Context cn;
    private List<Home> home;
    public static Picasso picasso;
        public static  PrefManager pref;
    public static   CommunityAdapter context;

    public CommunityAdapter(Context cn, List<Home> home,Picasso picasso,PrefManager pref) {
        this.cn = cn;
        this.home = home;
        this.picasso=picasso;
        this.pref=pref;
        this.context=this;
    }


    public void updateView(){
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(cn).inflate(R.layout.test_layout, parent, false);
                return new ViewHolder1(view);
            case 1:
                view = LayoutInflater.from(cn).inflate(R.layout.test_layout2, parent, false);
                return new ViewHolder2(view);
        }
        return null;

    }


     public  void  UpdateRecylerView(){
        notifyDataSetChanged();
     }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        Home hm = home.get(position);

        if (position%2 == 0){
            ((ViewHolder1) holder).Update(hm,position);
        } else {
            ((ViewHolder2) holder).Update(hm,position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position%2 == 0){
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return home.size();
    }

    public static class ViewHolder1 extends RecyclerView.ViewHolder {

        public View mView;
        public ImageView planetimg,planetlock,planetopen,planetcompleted;
        private RelativeLayout planet;
        public RatingBar planet_rating;
        private  TextView planetid,planetname ;


        public ViewHolder1(View itemView) {
            super(itemView);
            planetname = itemView.findViewById(R.id.planetname);
            planet = itemView.findViewById(R.id.planet);
            planetid = itemView.findViewById(R.id.planetid);
            planetimg = itemView.findViewById(R.id.planetimg);
            planetlock = itemView.findViewById(R.id.planetlock);
            planetopen = itemView.findViewById(R.id.planetopen);
            planet_rating = itemView.findViewById(R.id.planet_rating);
            planetcompleted = itemView.findViewById(R.id.planetcompleted);
            mView = itemView;
        }

        public void Update(Home hm,int position) {

            planetname.setText(hm.getTitle());

            if(hm.getStatus().equals("open")){
                planet_rating.setVisibility(View.GONE);
                planetlock.setVisibility(View.GONE);
                planetcompleted.setVisibility(View.GONE);


                planetopen.setVisibility(View.VISIBLE);
                planet.setOnClickListener(v -> {

                    context.updateView();
                    int id = (int) hm.getCount();
                    if (id != 0) {

                        cn.startActivity(new Intent(cn, CommunityViewPager.class)
                                .putExtra("count", id)
                                .putExtra("parcel", hm));
                    }

                    if (hm.getType().equals("double") || id == 0) {
                        List<com.example.test.programmingmama.Model.NewModel.List> lm = hm.getList();
                        cn.startActivity(new Intent(cn, CommunityMultipleModule.class)
                                .putExtra("parcel", hm));
                    }

                    if (hm.getType().equals("pc01")) {
                        List<com.example.test.programmingmama.Model.NewModel.List> lm = hm.getList();
                        cn.startActivity(new Intent(cn, ProgrammingChallenge.class)
                                .putExtra("parcel", hm));
                    }


                    if (hm.getType().equals("exp")) {
                        cn.startActivity(new Intent(cn, Explore_More.class));
                    }
                    if (hm.getType().equals("feed")) {
                        cn.startActivity(new Intent(cn, FeedBack_Module.class));
                    }
                    if (hm.getType().equals("crt")) {
                        cn.startActivity(new Intent(cn, Certificate.class));
                    }
                    if (hm.getType().equals("pc02")) {
                        cn.startActivity(new Intent(cn, Challenge.class));
                    }
                    if (hm.getType().equals("pc03")) {
                        cn.startActivity(new Intent(cn, Challenge3_Activity.class));
                    }

                    if (hm.getType().equals("pc04")) {
                        cn.startActivity(new Intent(cn, Final_Challenge.class));
                    }

                });

            }else if(hm.getStatus().equals("lock")){
                planet_rating.setVisibility(View.GONE);
                planetcompleted.setVisibility(View.GONE);
                planetopen.setVisibility(View.GONE);


                planetlock.setVisibility(View.VISIBLE);
                planet.setOnClickListener(v->{
                    Toast.makeText(cn, "Please Complete the previous module first", Toast.LENGTH_LONG).show();
                });

            }else if(hm.getStatus().equals("completed")){
                planetlock.setVisibility(View.GONE);
                planetopen.setVisibility(View.GONE);


                planetcompleted.setVisibility(View.VISIBLE);

                if (hm.getType().equals("pc01")||hm.getType().equals("pc03")||hm.getType().equals("pc02")) {
                    planet_rating.setVisibility(View.VISIBLE);
                } else {
                    planet_rating.setVisibility(View.VISIBLE);
                    float result =((float) hm.getResult()/hm.getTotal())*5;
                    planet_rating.setRating( (int)Math.round(result));
                }
                planet.setOnClickListener(v -> {
                    int id = (int) hm.getCount();
                    if (id != 0) {
                        cn.startActivity(new Intent(cn, CommunityViewPager.class)
                                .putExtra("count", id)
                                .putExtra("parcel", hm));
                    }

                    if (hm.getType().equals("double") || id == 0) {
                        cn.startActivity(new Intent(cn, CommunityMultipleModule.class)
                                .putExtra("parcel", hm));
                    }

                    if (hm.getType().equals("pc01")) {

                        cn.startActivity(new Intent(cn, ProgrammingChallenge.class));
                    }
                    if (hm.getType().equals("pc02")) {

                        cn.startActivity(new Intent(cn, Challenge.class));
                    }
                    if (hm.getType().equals("pc03")) {

                        cn.startActivity(new Intent(cn, Challenge3_Activity.class));
                    }
                    if (hm.getType().equals("pc04")) {
                        cn.startActivity(new Intent(cn, Final_Challenge.class));
                    }
                });

            }

            planetid.setText(""+(position+1)+"");
            int res;
            try{

                res = cn.getResources().getIdentifier("com.example.test.programmingmama:drawable/planet"+(position+1), null, null);
            }catch (Exception e) {
                Log.d("IMAGE", e.getMessage());
                res =R.drawable.planet1;
            }
            Glide.with(cn).load(res).into(planetimg);

        }


    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder {

        public View mView;
        public ImageView planetimg,planetlock,planetopen,planetcompleted;
        private RelativeLayout planet;
        public RatingBar planet_rating;
        private  TextView planetid,planetname ;

        public ViewHolder2(View itemView) {
            super(itemView);
            planetname = itemView.findViewById(R.id.planetname);
            planet = itemView.findViewById(R.id.planet);
            planetid = itemView.findViewById(R.id.planetid);
            planetimg = itemView.findViewById(R.id.planetimg);
            planetlock = itemView.findViewById(R.id.planetlock);
            planetopen = itemView.findViewById(R.id.planetopen);
            planet_rating = itemView.findViewById(R.id.planet_rating);
            planetcompleted = itemView.findViewById(R.id.planetcompleted);
            mView = itemView;
        }

        public void Update(Home hm,int position) {

            planetname.setText(hm.getTitle());


            if(hm.getStatus().equals("open")){
                planet_rating.setVisibility(View.GONE);
                planetlock.setVisibility(View.GONE);
                planetcompleted.setVisibility(View.GONE);

                planetopen.setVisibility(View.VISIBLE);
                planet.setOnClickListener(v -> {
                    pref.SetHomePosition(position);
                    context.updateView();
                    int id = (int) hm.getCount();
                    if (id != 0) {

                        cn.startActivity(new Intent(cn, CommunityViewPager.class)
                                .putExtra("count", id)
                                .putExtra("parcel", hm));
                    }

                    if (hm.getType().equals("double") || id == 0) {
                        List<com.example.test.programmingmama.Model.NewModel.List> lm = hm.getList();
                        cn.startActivity(new Intent(cn, CommunityMultipleModule.class)
                                .putExtra("parcel", hm));
                    }

                    if (hm.getType().equals("pc01")) {
                        List<com.example.test.programmingmama.Model.NewModel.List> lm = hm.getList();
                        cn.startActivity(new Intent(cn, ProgrammingChallenge.class)
                                .putExtra("parcel", hm));
                    }


                    if (hm.getType().equals("exp")) {
                        cn.startActivity(new Intent(cn, Explore_More.class));
                    }
                    if (hm.getType().equals("feed")) {
                        cn.startActivity(new Intent(cn, FeedBack_Module.class));
                    }
                    if (hm.getType().equals("crt")) {
                        cn.startActivity(new Intent(cn, Certificate.class));
                    }
                    if (hm.getType().equals("pc02")) {
                        cn.startActivity(new Intent(cn, Challenge.class));
                    }
                    if (hm.getType().equals("pc03")) {
                        cn.startActivity(new Intent(cn, Challenge3_Activity.class));
                    }

                    if (hm.getType().equals("pc04")) {
                        cn.startActivity(new Intent(cn, Final_Challenge.class));
                    }

                });
            }else if(hm.getStatus().equals("lock")){
                planet_rating.setVisibility(View.GONE);
                planetcompleted.setVisibility(View.GONE);
                planetopen.setVisibility(View.GONE);

                planetlock.setVisibility(View.VISIBLE);
                planet.setOnClickListener(v->{
                    Toast.makeText(cn, "Please Complete the previous module first", Toast.LENGTH_LONG).show();
                });
            }else if(hm.getStatus().equals("completed")){
                planetlock.setVisibility(View.GONE);
                planetopen.setVisibility(View.GONE);

                planetcompleted.setVisibility(View.VISIBLE);

                if (hm.getType().equals("pc01")||hm.getType().equals("pc03")||hm.getType().equals("pc02")) {
                    planet_rating.setVisibility(View.VISIBLE);
                } else {
                    planet_rating.setVisibility(View.VISIBLE);
                    float result =((float) hm.getResult()/hm.getTotal())*5;
                    planet_rating.setRating( (int)Math.round(result));
                }
                planet.setOnClickListener(v -> {
                    int id = (int) hm.getCount();
                    if (id != 0) {
                        cn.startActivity(new Intent(cn, CommunityViewPager.class)
                                .putExtra("count", id)
                                .putExtra("parcel", hm));
                    }

                    if (hm.getType().equals("double") || id == 0) {
                        cn.startActivity(new Intent(cn, CommunityMultipleModule.class)
                                .putExtra("parcel", hm));
                    }

                    if (hm.getType().equals("pc01")) {

                        cn.startActivity(new Intent(cn, ProgrammingChallenge.class));
                    }
                    if (hm.getType().equals("pc02")) {

                        cn.startActivity(new Intent(cn, Challenge.class));
                    }
                    if (hm.getType().equals("pc03")) {

                        cn.startActivity(new Intent(cn, Challenge3_Activity.class));
                    }
                    if (hm.getType().equals("pc04")) {
                        cn.startActivity(new Intent(cn, Final_Challenge.class));
                    }
                });

            }


            planetid.setText(""+(position+1)+"");
            int res;
            try{
                res = cn.getResources().getIdentifier("com.example.test.programmingmama:drawable/planet"+(position+1), null, null);
            }catch (Exception e) {
                Log.d("IMAGE", e.getMessage());
                res =R.drawable.planet1;
            }
            Glide.with(cn).load(res).into(planetimg);

        }

    }

}
