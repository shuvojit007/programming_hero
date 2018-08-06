package com.example.test.programmingmama.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Explore_Model extends ExpandableGroup {
    public String title;


    public Explore_Model(String title, List items) {
        super(title, items);
    }
}
