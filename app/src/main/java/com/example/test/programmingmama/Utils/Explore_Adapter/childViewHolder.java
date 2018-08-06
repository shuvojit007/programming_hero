package com.example.test.programmingmama.Utils.Explore_Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.test.programmingmama.R;
import com.squareup.picasso.Picasso;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.realm.RealmResults;

public class childViewHolder extends ChildViewHolder {
    private TextView childName,caption;
    private RelativeLayout relativeLayout;
    private ImageView imageView;
    private LinearLayout exp_item_lin;




    public childViewHolder(View itemView) {
        super(itemView);
        exp_item_lin =itemView.findViewById(R.id.exp_item_lin);
       childName =itemView.findViewById(R.id.titleTxt);
        caption =itemView.findViewById(R.id.captionTxt);
        imageView = itemView.findViewById(R.id.imageView);
//        dropdown = itemView.findViewById(R.id.rec_child_dropdown);
//        addlist = itemView.findViewById(R.id.rec_child_add);
//        relativeLayout = itemView.findViewById(R.id.rec_child_relayout);


    }




    public void setOnClikLin(String text,Context cn){
        exp_item_lin.setOnClickListener(vv->{
      cn.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(text)));
        });
    }


    public void setChildName(String text){
        childName.setVisibility(View.VISIBLE);
        childName.setText(text);
    }

    public void setCaption(String captiontext ){
        caption.setText(captiontext);


    }

    public void setimageView(String image, Context cn){
        //Glide.with().load(image).into(imageView);
        Picasso.get().load(image).into(imageView);

    }
}
