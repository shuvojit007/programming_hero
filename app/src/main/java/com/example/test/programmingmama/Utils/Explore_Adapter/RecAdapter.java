package com.example.test.programmingmama.Utils.Explore_Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.test.programmingmama.Model.Explore_Info;
import com.example.test.programmingmama.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class RecAdapter extends ExpandableRecyclerViewAdapter<parentViewHolder,childViewHolder> {

    Activity cn;
    public RecAdapter(List<? extends ExpandableGroup> groups, Context context) {
        super(groups);
        this.cn = (Activity) context;

    }

    @Override
    public parentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.explore_heading,parent,false);
        return new parentViewHolder(view);
    }

    @Override
    public childViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.explore_item,parent,false);
            return new childViewHolder(view);
    }



    @Override
    public void onBindChildViewHolder(childViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        Explore_Info cm = (Explore_Info) group.getItems().get(childIndex);
      //  holder.setIcon(cm.getIcon());
        holder.setChildName(cm.getTitle());
        holder.setCaption(cm.getCaption());

        holder.setOnClikLin(cm.getLink(),cn);
        holder.setimageView(cm.getImageUrl(),cn);
//        if(!cm.getTitle().equalsIgnoreCase("Add New Task")||
//                !cm.getTitle().equalsIgnoreCase("Active Reminders")){
//            holder.setAddlist(cm.getAdd());
//            holder.setDropdown(cm.getDropdown());
//        }
    }

    @Override
    public void onBindGroupViewHolder(parentViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setParentName(group.getTitle());


    }


}

